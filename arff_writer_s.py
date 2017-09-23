# open file arff
fo = open("tag_ID_s.arff", "w")
fo.write("\
@RELATION POS_TAG\n\
@ATTRIBUTE text string\n\
@ATTRIBUTE class {ADJ, ADP, ADV, AUX, CCONJ, DET, INTJ, NOUN, \
NUM, PART, PRON, PROPN, PUNCT, SCONJ, SYM, VERB, X}\n\n\
@DATA\n\
")

# open conllu file 
fc = open("ud-treebanks-conll2017\UD_Indonesian/id-ud-train.conllu")
pre_tag = ""
for line in fc:
	text_array = line.split()
	if (len(text_array) > 1) :
		if (text_array[0] != '#'):

			# write token
			if (text_array[1] == ','):
				fo.write('(koma)')
			elif (text_array[1] == '\''):
				fo.write('(petiksatu)')
			elif (text_array[1] == '\"'):
				fo.write('(petikdua)')
			elif ('\'s' in text_array[1]):
				fo.write('\"')
				fo.write(text_array[1])
				fo.write('\"')
			elif (',' in text_array[1] and len(text_array[1]) > 1):
				text = text_array[1].replace(',', '.')
				fo.write(text)
			else:
				fo.write(text_array[1])
			fo.write(',')

			# write class	
			fo.write(text_array[3])
			pre_tag = text_array[3]
			fo.write('\n')
fc.close()
fo.close()