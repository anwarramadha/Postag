/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author AnwarRamadha
 */
import weka.core.Instances;
import weka.classifiers.bayes.*;
import weka.classifiers.*;
import java.io.*;
import java.util.*;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;

public class Postag_pre_word 
{	
    private static class Attributes
    {
        public String text;
        public String pre_word;
        public String cls;
        
        public Attributes(String text, String pre_word, String cls) {
            this.text = text;
            this.pre_word = pre_word;
            this.cls = cls;
        }
        
        public void setPreTag(String pre_word) {
            this.pre_word = pre_word;
        }
        
        public void setClass(String  cls) {
            this.cls = cls;
        }
    }
    public static NaiveBayesMultinomialText classify_with_pre_word(Instances data) {
            try {
                // train data
                NaiveBayesMultinomialText nb = new NaiveBayesMultinomialText();
                nb.buildClassifier(data);
                System.out.println("Train done\n");
                return nb;
//                    System.out.println(eval.toSummaryString("\nResults\n======\n", false));

            }
            catch(Exception e) {
                System.out.println(e.toString());
                return null;
            }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Nama dataset: tag_ID.arff");
        Scanner sc = new Scanner(System.in);
        String dataset = "tag_ID_pre_word.arff";

        BufferedReader reader = new BufferedReader(new FileReader(dataset));
        Instances data = new Instances(reader);
        reader.close();
        data.setClassIndex(data.numAttributes() - 1);
        NaiveBayesMultinomialText nb =  Postag_pre_word.classify_with_pre_word(data);

        System.out.println("Masukkan text : ");
        String str = sc.nextLine();
        List text_string = new ArrayList();

        String [] char_arr = {",","\'","\"", "!",  ":", "(",")",  "+",  "-",  "_",  "#",  "%", 
        "$",  "?", ".", ";", "/","\\", "[", "]", "|", "~"};

        List<String> char_list = Arrays.asList(char_arr);

        String [] char_name = {"(koma)", "(petiksatu)","(petikdua)","(seru)","(titikdua)","(bukakurung)","(tutupkurung)","(tambah)",
                        "(kurang)","(underscore)","(pagar)","(persen)","(dollar)","(tanya)", "(titik)", "(titikkoma)", "(slice)",
                        "(backslice)", "(bukakurungsiku)", "(tutupkurungsiku)", "(or)", "(~)"};

        String delimiter = "(?<=[\\W&&\\S]+)|(?=[\\W&&\\S]+)|[\\s]";
        for (String s : str.split(delimiter)) {
            if (!s.contains("\\s") && s.length() >= 1) {
               
                    if (char_list.contains(s)) text_string.add(char_name[char_list.indexOf(s)]);
                    else text_string.add(s.replace(" ", ""));
            }
        }
        
        String pre_word = "";
        System.out.println("\n\n");

        int count_dash = 31;
        System.out.print(" ");
        for(int i=0; i < count_dash; i++) System.out.print("-");
        System.out.print("\n|Text");
        for (int i=0; i < 20 - ("|Text").length()+1; i++) System.out.print(" ");
        System.out.print("|\tClass\t|\n");
        System.out.print(" ");
        for(int i=0; i < count_dash; i++) System.out.print("-");

        System.out.println();
        List<String> char_name_list = Arrays.asList(char_name);
        for (int i = 0; i < text_string.size(); i++) {
            
            ArrayList<Attribute> atts = new ArrayList<Attribute>(3);
            ArrayList<String> classVal = new ArrayList<String>();

            classVal.add("ADJ");
            classVal.add("ADF");
            classVal.add("ADV");
            classVal.add("AUX");
            classVal.add("CCONJ");
            classVal.add("DET");
            classVal.add("INTJ");
            classVal.add("NOUN");
            classVal.add("NUM");
            classVal.add("PART");
            classVal.add("PRON");
            classVal.add("PROPN");
            classVal.add("PUNCT");
            classVal.add("SCONJ");
            classVal.add("SYM");
            classVal.add("VERB");
            classVal.add("X");

            atts.add(new Attribute("text",(ArrayList<String>)null));
            atts.add(new Attribute("pre_word",(ArrayList<String>)null));
            atts.add(new Attribute("class",classVal));
            
            Attributes att;
            
            if (i == 0) {
                att = new Attributes(text_string.get(i).toString(), 
                                        "NULL", "?");
            }
            else {
                att = new Attributes(text_string.get(i).toString(), 
                                        pre_word, "?");
            }
            
            Instances dataRaw = new Instances("POS_TAG",atts,0);
            dataRaw.setClassIndex(2);

            DenseInstance denseInstance1 = new DenseInstance(3);

            denseInstance1.setValue(atts.get(0), att.text);
            denseInstance1.setValue(atts.get(1), att.pre_word);


            dataRaw.add(denseInstance1);

            double pred = nb.classifyInstance(dataRaw.lastInstance());
            att.cls = dataRaw.classAttribute().value((int) pred);
            pre_word = att.text;

            String text = char_name_list.contains(att.text) ? char_arr[char_name_list.indexOf(att.text)] : att.text;

            System.out.print("|"+text);

            int count_space = 20 - text.length();

            for (int j=0; j < count_space; j++) {
                System.out.print(" ");
            }

            System.out.println("|\t"+att.cls+"\t|");
        }

        System.out.print(" ");
        for(int i=0; i < count_dash; i++) System.out.print("-");
    }
}