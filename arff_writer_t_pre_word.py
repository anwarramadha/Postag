# open file arff
fo = open("tag_ID_pre_word.arff", "w")
fo.write("\
@RELATION POS_TAG\n\
@ATTRIBUTE text string\n\
@ATTRIBUTE pre_word string\n\
@ATTRIBUTE class {ADJ, ADP, ADV, AUX, CCONJ, DET, INTJ, NOUN, \
NUM, PART, PRON, PROPN, PUNCT, SCONJ, SYM, VERB, X}\n\n\
@DATA\n\
")

# open conllu file 
fc = open("ud-treebanks-conll2017\UD_Indonesian/id-ud-train.conllu")
pre_word = ""
for line in fc:
	text_array = line.split()
	if (len(text_array) > 1) :
		char_list = [',','\'','\"', '!',  ':', '(',')',  '+',  '-',  '_',  '#',  '%', '$',  '?', '.', ';', '/','\\', '[', ']', '|', '~']

		char_name = ['(koma)', '(petiksatu)','(petikdua)','(seru)','(titikdua)','(bukakurung)','(tutupkurung)','(tambah)',
						'(kurang)','(underscore)','(pagar)','(persen)','(dollar)','(tanya)', '(titik)', '(titikkoma)', '(slice)',
						'(backslice)', '(bukakurungsiku)', '(tutupkurungsiku)', '(or)', '(~)']

		if (text_array[0] != '#'):

			# write token
			
			if ('\'s' in text_array[1]):
				fo.write('\"')
				fo.write(text_array[1])
				fo.write('\"')
			elif (',' in text_array[1] and len(text_array[1]) > 1):
				text = text_array[1].replace(',', '.')
				fo.write(text)
			elif (text_array[1] in char_list):
				fo.write(char_name[char_list.index(text_array[1])])
			else:
				fo.write(text_array[1])
			fo.write(',')

			# write pre tag
			if (text_array[0] == '1'):
				fo.write('NULL')
			else:
				if pre_word in char_list:
					fo.write(char_name[char_list.index(pre_word)])
				elif '\'s' in pre_word:
					fo.write('\"')
					fo.write(pre_word)
					fo.write('\"')
				elif (',' in pre_word and len(pre_word) > 1):
					text = pre_word.replace(',', '.')
					fo.write(text)
				else:
					fo.write(pre_word)

			fo.write(',')

			# write class	
			fo.write(text_array[3])
			pre_word = text_array[1]
			fo.write('\n')
fc.close()
fo.close()