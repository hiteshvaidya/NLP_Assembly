

a = ['store','accumulator']
b = [['store','move','transfer'],['register','address','memory'],['location','data','accumulator','store']]

c = [list(set(a).intersection(i)) for i in b]
print(c)

'''
if set(a).issubset(b):
    print("subset found")
else:
    print("no subset found")
'''
