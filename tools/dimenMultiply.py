import  xml.etree.ElementTree as ET
import sys

####CommentedTreeBuilder for saving comment when write and read
class CommentedTreeBuilder(ET.XMLTreeBuilder):
    def __init__(self, html = 0, target = None):
        ET.XMLTreeBuilder.__init__( self, html, target )
        self._parser.CommentHandler = self.handle_comment

    def handle_comment(self, data):
        self._target.start(ET.Comment, {})
        self._target.data(data)
        self._target.end(ET.Comment)

def tranferDimen(infile, outfile, multi):
    tree = ET.parse(infile, parser = CommentedTreeBuilder())
    nodelist = tree.findall("dimen")
    for node in nodelist:
        node.text = multi_dimen_text(node.text, multi)
    tree.write(outfile, encoding="utf-8",xml_declaration=True)

def multi_dimen_text(text, multi):
    if(text.endswith('sp')):
        temp = text.strip('sp')
        value = str(round(float(temp) * float(multi), 1))
        if(value.endswith('.0')):
            #value = value.strip('.0')
            value = value[:-2]
        formatValue = value + 'sp'
    elif(text.endswith('dp')):
        temp = text.strip('dp')
        value = str(round(float(temp) * float(multi), 1))
        if(value.endswith('.0')):
            #value = value.strip('.0')
            value = value[:-2]
        formatValue = value + 'dp'
    elif(text.endswith('px')):
        temp = text.strip('px')
        value = str(round(float(temp), 1))
        if(value.endswith('.0')):
            #value = value.strip('.0')
            value = value[:-2]
        formatValue = value + 'px'
    return formatValue

####main entrance
size = len(sys.argv)
if(size < 4):
    print "example:python dimenEx.py inFile=filepath outFile=filepath multi=1.5"
    print "inFile: input file path"
    print "outFile: output file path"
    print "multi: base value"
else:
    infile = sys.argv[1].split('=')[1]
    outfile = sys.argv[2].split('=')[1]
    multi = sys.argv[3].split('=')[1]
    tranferDimen(infile, outfile, multi)

