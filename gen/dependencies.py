#!/usr/bin/env python3
import os
import pathlib


PROJECT_DIR = "../src/main"
OUTPUT_FILE = "./dependencies.puml"

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

PACKAGE_NAMES = [
    "de.dhbw.bahn.schicht_4_abstraktion",
    "de.dhbw.bahn.schicht_3_domaene",
    "de.dhbw.bahn.schicht_2_anwendung",
    "de.dhbw.bahn.schicht_1_adapter",
    "de.dhbw.bahn.schicht_0_plugins"
]

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
        lines = [f"package {self.name} {{"]
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
