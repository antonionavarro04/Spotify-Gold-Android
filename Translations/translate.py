import xml.etree.ElementTree as ET
from googletrans import Translator

translator = Translator()

# Languages located in ./input/languages.csv
with open("input/languages.csv", "r") as file:
    langs = file.read().split(",")

for lang in langs:
    # Create a file in output/ for each language
    with open(f"output/{lang}.xml", "w") as file:
        file.write("<resources>\n")

# XML string
with open("input/input.xml", "r") as file:
    xml_data = file.read()

# Parse the XML string
root = ET.fromstring(xml_data)

values: list[str] = []

# Iterate through each 'string' element and print the name and text
for lang in langs:
    with open(f"output/{lang}.xml", "a", encoding='utf-8') as file:        
        for e in root.findall('string'):
            try:
                if e.attrib['translatable'] == "false":
                    continue
            except:
                pass

            try:
                tranlation = translator.translate(text=e.text, dest=lang, src="en").text.replace("'", "\\'")
            except:
                print(f"Unknown Language: {lang}")
            out = f"<string name=\"{e.attrib['name']}\">{tranlation}</string>"
            file.write(out + "\n")

        file.write("</resources>")
