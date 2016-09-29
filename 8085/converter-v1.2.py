
'''
Following code accepts commands of assembly language in natural language and gives the output as .asm/.txt file

-created by
Hitesh Vaidya
'''

import csv

class Read:
    
    def __init__(self):
        self.thesaures = []                                     #stores thesaures from .csv file
        self.cmd = []                                           #stores assembly instructions from .csv file
        self.lines = []                                         #stores input sentences as list of english words
        self.catIndex = []                                      #stores categories of instructions from .csv file
            
    def ReadFile(self):
        self.fileName = input('Enter the input file name: ')     #read input file
        self.file = open(self.fileName,'r')
        self.readFile = self.file.readlines()
        
        for counter in self.readFile:
            temp1 = counter.rstrip()
            temp2 = str()
            if temp2 is temp1:
                break
            temp2 = temp1.split()
            temp2[0] = temp2[0].lower()                          #we can lower only 1st word bcoz later registers and address are in caps
            self.lines.append(temp2)
        self.file.close()
        

    def readCol(self):                                           #stores the columns from .csv file.
        with open('mnemonics.csv') as csvfile:
            self.readCSV = csv.reader(csvfile,delimiter=',')
            for self.row in self.readCSV:                                  #stores the thesaures of each command as a list by eliminating ','
                temp3 = self.row[0].split(';')
                temp4 = self.row[1]
                temp5 = self.row[2].split(';')
                self.thesaures.append(temp3)                     #thesaures is now list of list.
                self.cmd.append(temp4)                           #prepares list of assembly language commands
                self.catIndex.append(temp5)                      #prepares list of categories of each instruction
            del self.thesaures[0],self.cmd[0],self.catIndex[0]   #deletes the first row of column names
            
            
class Categorize:

    def __init__(self,lines):
        self.lines = lines                                      #stores the words in input sentences
        self.data = []                                                       #stores the list of data
        self.locations = []                                                  #stores the list of addresses
        self.operands = []                                      #stores operands from input sentences
        self.type = []                                          #stores the type of instruction by analyzing input sentence
        self.category = []                                      #stores category by reading ict.csv file
        
    def identifyType(self):
        with open('ict.csv') as ictfile:
            self.readICT = csv.reader(ictfile,delimiter=',')
            for self.row in self.readICT:
                self.temp = self.row[1].split(';')
                self.category.append(self.temp)
            del self.category[:2]
    

    def identify(self):                                               #identifies the data and addresses
                
        for i in range(len(self.lines)):
            self.tmp1 = []
            self.tmp2 = []
            for j in range(len(self.lines[i])):
                if self.lines[i][j].startswith("#"):
                    self.data.append(self.lines[i][j][1:])                     #adds data to self.data list
                    self.tmp1.insert(0,self.lines[i][j][1:])                #operands are inserted from start thus giving us <dest,source>
                    self.tmp2.insert(0,"data")
                elif self.lines[i][j].startswith("@"):
                    self.locations.append(self.lines[i][j][1:])                #adds address to self.locations list
                    self.tmp1.insert(0,self.lines[i][j][1:])
                    self.tmp2.insert(0,"address")
                elif self.lines[i][j].startswith("HL"):
                    self.tmp1.insert(0,"H")
                    self.tmp2.insert(0,"memory")
                elif self.lines[i][j].startswith("BC"):
                    self.tmp1.insert(0,"B")
                    self.tmp2.insert(0,"memory")
                elif self.lines[i][j].startswith("DE"):
                    self.tmp1.insert(0,"D")
                    sef.tmp2.insert(0,"memory")
                elif self.lines[i][j].startswith("B"):
                    self.tmp1.insert(0,"B")
                    self.tmp2.insert(0,"register")
                elif self.lines[i][j].startswith("C"):
                    self.tmp1.insert(0,"C")
                    self.tmp2.insert(0,"register")
                elif self.lines[i][j].startswith("D"):
                    self.tmp1.insert(0,"D")
                    self.tmp2.insert(0,"register")
                elif self.lines[i][j].startswith("E"):
                    self.tmp1.insert(0,"E")
                    self.tmp2.insert(0,"register")
                elif self.lines[i][j] in ['accumulator','acc','A']:
                    self.tmp1.insert(0,'A')
                    self.tmp2.insert(0,"acc")
            self.operands.append(self.tmp1)
            self.type.append(self.tmp2)
            self.lines[i] = [x.lower() for x in self.lines[i]]
        
        self.typeIndex = []
        for i in self.type:
            if not i:
                self.typeIndex.append(0)
                continue
            for j in self.category:
                if i == j:
                    self.typeIndex.append(self.category.index(j))

        return self.typeIndex


class Check:
    
    def __init__(self,thesList,cmdList,lineList):
        self.words = thesList                                    #self.words contains list of thesaures
        self.instructions = cmdList                              #self.instructions contains list of command words
        self.ipLines = lineList                                  #self.ipLines contains list of words in input english lines
        self.match = []                                          #stores the matched instructions
        
    def subset(self,typeIndex,catIndex,operands):
        self.typeIndex = typeIndex
        self.catIndex = catIndex
        self.operands = operands
        self.output = str()
        
        for i in self.ipLines:
            self.temp = [list(set(i).intersection(j)) for j in self.words]      #for each input line check intersection with all thesaures
            self.match.append(self.temp)                         #self.temp is list of intersection with all thesaures i.e. list of list

        for i in range(len(self.match)):
            for j in range(len(self.match[i])):
                if self.match[i][j] and (str(self.typeIndex[i]) in self.catIndex[j]):
                        print(self.instructions[j],end=" ")
                        self.output += '\n' + str(self.instructions[j]) + ' '
                        if 'A' in self.operands[i]:
                            self.operands[i].remove('A')
                        print(','.join(self.operands[i]))
                        self.output += str(','.join(self.operands[i]))

        return self.output
        
            
rd = Read()
rd.ReadFile()
rd.readCol()

ctg = Categorize(rd.lines)
ctg.identifyType()
typeIndex = ctg.identify()
        
chk = Check(rd.thesaures,rd.cmd,rd.lines)
outputText = chk.subset(typeIndex,rd.catIndex,ctg.operands)

opFileName = input("Enter the name of output file to be created along with file extension\n")
#opFileName = 'output\\' + opFileName
create = open(opFileName,'w')
create.write(outputText[1:])
create.close()
