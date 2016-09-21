import csv

class Read:
    
    def __init__(self):
        self.thesaures = []                                     #stores thesaures from .csv file
        self.cmd = []                                           #stores assembly instructions from .csv file
        self.lines = []                                         #stores input sentences as list of english words
        self.category = []                                      #stores categories of instructions from .csv file
            
    def ReadFile(self):
        self.fileName = input('Enter the input file name: ')     #read input file
        self.readFile = open(self.fileName,'r').readlines()
        
        for counter in self.readFile:
            temp1 = counter.rstrip()
            temp2 = str()
            if temp2 is temp1:
                break
            temp2 = temp1.split()
            temp2[0] = temp2[0].lower()                          #we can lower only 1st word bcoz later registers and address are in caps
            self.lines.append(temp2)
        

    def readCol(self):                                           #stores the columns from .csv file.
        with open('mnemonics.csv') as csvfile:
            self.readCSV = csv.reader(csvfile,delimiter=',')
            for row in self.readCSV:                                  #stores the thesaures of each command as a list by eliminating ','
                temp3 = row[0].split(';')
                temp4 = row[1]
                temp5 = row[2].split(';')
                self.thesaures.append(temp3)                     #thesaures is now list of list.
                self.cmd.append(temp4)                           #prepares list of assembly language commands
                self.category.append(temp5)                      #prepares list of categories of each instruction
            self.thesaures.pop(0)                                #pops out the word 'thesaures'
            self.cmd.pop(0)                                      #pops out the word 'instruction'
            self.category.pop(0)
            
class Categorize:

    def __init__(self,lines):
        self.lines = lines                                      #stores the words in input sentences
        self.data = []                                                       #stores the list of data
        self.locations = []                                                  #stores the list of addresses
        self.type = []                                          #stores type of input sentence
        self.operands = []                                      #stores operands from input sentences
        
    def identify(self):                                               #identifies the data and addresses
        String tmp = str()
        for i in range(len(self.lines)):
            for j in range(len(self.lines[i])):
                if self.lines[i][j].startswith("#"):
                    self.data.append(self.lines[i][j][1:])                     #adds data to self.data list
                    self.operands.append(self.lines[i][j][1:])
                    tmp += "d"
                elif self.lines[i][j].startswith("@"):
                    self.locations.append(self.lines[i][j][1:])                #adds address to self.locations list
                    self.operands.append(self.lines[i][j][1:])
                    tmp += "l"
                elif self.lines[i][j].startswith("H"):
                    self.operands.append("H")
                    tmp += "r"
                elif self.lines[i][j].startswith("B"):
                    self.operands.append("B")
                    tmp += "r"
                elif self.lines[i][j] in ['accumulator','acc','A']:
                    self.operands.append('accumulator')
                    tmp += "a"

    def identifyType:
        for i in self.lines:
            


class Check:
    
    def __init__(self,thesList,cmdList,lineList):
        self.words = thesList                                    #self.words contains list of thesaures
        self.instructions = cmdList                              #self.instructions contains list of command words
        self.ipLines = lineList                                  #self.ipLines contains list of words in input english lines
        self.match = []                                          #stores the matched instructions
        
    def subset(self):
        for i in self.ipLines:
            self.temp = [list(set(i).intersection(j)) for j in self.words]      #for each input line check intersection with all thesaures
            self.match.append(self.temp)                         #self.temp is list of intersection with all thesaures i.e. list of list
        for i in range(len(self.match)):
            for j in range(len(self.match[i])):
                if self.match[i][j]:
                    print(self.instructions[j],end=" ")
            print()
        
            
rd = Read()
rd.ReadFile()
rd.readCol()

ctg = Categorize(rd.lines)
            
chk = Check(rd.thesaures,rd.cmd,rd.lines)
chk.subset()
