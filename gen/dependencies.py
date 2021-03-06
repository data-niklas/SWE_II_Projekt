#!/usr/bin/env python3
import os
import pathlib
import random


PROJECT_DIR = "../src/main"
OUTPUT_FILE = "./dependencies.puml"

HOVER_STYLE = """
<style type="text/css"><![CDATA[
    path:hover + polygon, path:hover {
        stroke: #ff008c !important;
        stroke-width: 8 !important;
    }

]]>
</style>
"""

HEADER = """
@startuml
<style>
    classDiagram{
        FontColor Black
        BackgroundColor White
        LineThickness 2
        LineColor Black
        RoundCorner 10
    }

</style>
skinparam useBetaStyle true
"""
"""
skinparam linetype ortho
"""


FOOTER = """
@enduml
"""

ROOT_PACKAGE_NAME = "de.dhbw.bahn"


class Package():
    def __init__(self, name):
        self.name = name
        self.packages = dict()
        self.classes = list()

    def add(self, package_name, class_name, dependencies):
        if self.name == package_name:
            self.classes.append((class_name, dependencies))
        else:
            subpackage_name = self.name + "." + package_name.replace(self.name, "")[1:].split(".")[0]
            if subpackage_name not in self.packages:
                self.packages[subpackage_name] = Package(subpackage_name)
            self.packages[subpackage_name].add(package_name, class_name, dependencies)


    def gen_structure(self):
        r = random.randint(200, 255)
        g = random.randint(200, 255)
        b = random.randint(200, 255)
        c = (r << 16) + (g << 8) + b
        color = hex(c).replace("0x", "#")

        lines = [f"package {self.name} {color}{{"]
        for (class_name, _) in self.classes:
            lines.append(f"class {class_name}{{\n}}")
        for k in self.packages:
            lines.append(self.packages[k].gen_structure())
        lines.append("}")
        return "\n".join(lines)

    def gen_refs(self):
        lines = list()
        for (class_name, dependencies) in self.classes:
            for d in dependencies:
                lines.append(f"{class_name} --> {d}")

        for k in self.packages:
            lines.append(self.packages[k].gen_refs())
        return "\n".join(lines)

def find_java_files(dir):
    return pathlib.Path(dir).rglob("*.java")

def generate_header(file):
    file.write(HEADER)

def generate_footer(file):
    file.write(FOOTER)

def analyze_java_file(file_name):
    file = open(file_name, 'r')
    lines = file.readlines()
    file.close()

    # Remove trailing .java
    name = str(file_name.name)[:-5]
    package = ""
    dependencies = list()

    for line in lines:
        line = line.strip()
        if line.startswith("package "):
            package = line[8:-1]
        elif line.startswith("import " + ROOT_PACKAGE_NAME):
            dependency_path = line[8:-1]
            parts = dependency_path.split(".")
            dependency = parts[-1]
            dependencies.append(dependency)

    print(name, dependencies, package)
    return package, name, dependencies


def analyze_java_files(files):
    package = Package(ROOT_PACKAGE_NAME)

    for file in files:
        package_name, class_name, dependencies = analyze_java_file(file)
        package.add(package_name, class_name, dependencies)

    return package

def apply_hover_effect(file_name):
    with open(file_name, 'r') as file:
        content = file.read()
    content = content.replace("<defs>", HOVER_STYLE + "<defs>")
    with open(file_name, 'w') as file:
        file.write(content)

if __name__ == "__main__":
    files = find_java_files(PROJECT_DIR)
    package = analyze_java_files(files)

    with open(OUTPUT_FILE, 'w') as out_file:
        generate_header(out_file)
        out_file.write(package.gen_structure())
        out_file.write("\n")
        out_file.write(package.gen_refs())
        out_file.write("\n")
        generate_footer(out_file)

    os.system(f"PLANTUML_LIMIT_SIZE=8192 plantuml -tsvg {OUTPUT_FILE}")
    os.system(f"PLANTUML_LIMIT_SIZE=8192 plantuml -tpng {OUTPUT_FILE}")
    apply_hover_effect(OUTPUT_FILE.replace("puml", "svg"))
