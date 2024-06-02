import xml.etree.ElementTree as ET
from googletrans import Translator

translator = Translator()

langs = ['zh-CN', 'es', 'fr', 'de', 'it', 'pt', 'ja', 'ru', "ar"]

for lang in langs:
    # Create a file in output/ for each language
    with open(f"output/{lang}.xml", "w") as file:
        file.write("")

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

            tranlation = translator.translate(text=e.text, dest=lang, src="en").text
            out = f"<string name=\"{e.attrib['name']}\">{tranlation}</string>"
            file.write(out + "\n")
