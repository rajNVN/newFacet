package preprocess;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

public class DataPreprocess {

    public static void cleanXML(File file) throws IOException {
        System.setOut(new PrintStream("removebadcharacters.txt"));
        long start = System.nanoTime();
        //String folder = "/run/media/rajnvn/RAM/OUTPUT/cleanedxml";
        String folder = "G:\\OUTPUT\\cleanedxml";
        for (File fil : file.listFiles()) {
            Scanner scn = new Scanner(fil);
            BufferedWriter bf = new BufferedWriter(new FileWriter(folder + "\\" + fil.getName()));
            System.out.println(folder + "\\" + fil.getName());
            while (scn.hasNextLine()) {
                String line = scn.nextLine();
                System.out.println(line);
                line = line.replaceAll("&#x[a-zA-Z0-9]*;", "");
                bf.write(line);
            }
            bf.close();
        }
        System.out.println("Time taken for cleanXML() : " + (double) (System.nanoTime() - start) / 1000000000);
    }


    public static void extractXML(File file) throws ParserConfigurationException, IOException, SAXException {
        //String folder = "/run/media/rajnvn/RAM/OUTPUT";
        String folder = "G:\\OUTPUT";
        for (File fil : file.listFiles()) {
            try {
                Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fil);
                NodeList documentList = document.getElementsByTagName("document");
                String fname = fil.getName();
                fname = fname.substring(0, fname.length() - 4);
                String folderPath = folder + "/" + fname;
                new File(folderPath).mkdir();
                for (int i = 0; i < documentList.getLength(); i++) {
                    String docPath = folderPath + "/" + i;
                    new File(docPath).mkdir();
                    BufferedWriter Text = new BufferedWriter(new FileWriter(docPath + "/text.txt"));
                    BufferedWriter Html = new BufferedWriter(new FileWriter(docPath + "/html.html"));
                    BufferedWriter Repeat = new BufferedWriter(new FileWriter(docPath + "/repeat.txt"));
                    NodeList nodeList = documentList.item(i).getChildNodes();
                    for (int j = 0; j < nodeList.getLength(); j++) {
                        Node node = nodeList.item(j);
                        switch (node.getNodeName()) {
                            case "title":
                                Text.write(node.getTextContent() + "\n");
                                break;
                            case "desc":
                                Text.write(node.getTextContent() + "\n");
                                break;
                            case "DocText":
                                Text.write(node.getTextContent() + "\n");
                                break;
                            case "RepeatRegionAnchorList":
                                Repeat.write(node.getTextContent() + "\n");
                                break;
                            case "ConvertedHtml":
                                Html.write(node.getTextContent() + "\n");
                        }
                    }
                    Text.close();
                    Html.close();
                    Repeat.close();
                }
            } catch (SAXException e) {
            }
        }
    }


    public static void parseHtml(File file) throws IOException {
        org.jsoup.nodes.Document document = Jsoup.parse(file, "UTF-8", "http://example.com/");
        Elements element = document.body().select("*");
        //System.out.println(element.text());
        for (org.jsoup.nodes.Element node : element) {
            String text = node.ownText();
            if (text.length() > 0)
                System.out.println(text);
        }
    }

    public static void parseRepeat(File file) throws FileNotFoundException {
        System.setOut(new PrintStream("listFile.txt"));
        Scanner input = new Scanner(file);
        while (input.hasNextLine()) {
            String line = input.nextLine();
            if (!line.isEmpty()) {
                line = line.substring(1);
                for (String subLine : line.split("\\)\\(")) {
                    if (subLine.startsWith("Useless"))
                        continue;
                    subLine = subLine.substring(subLine.indexOf(":#:") + 3);
                    System.out.println(subLine.substring(0, subLine.indexOf(":#:")));
                }
            }
        }
    }

    public static void combineAll(File mainFolder) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter("combine.txt"));
        for (File folder : Objects.requireNonNull(mainFolder.listFiles())) {
            //System.out.println("------Folder : "+folder.getName()+"---->");
            for (File document : Objects.requireNonNull(folder.listFiles())) {
                //new File("/run/media/rajnvn/RAM/OUTPUT/combined/"+folder.getName()).mkdir();
                new File("G:\\OUTPUT\\combined\\" + folder.getName()).mkdir();
                //System.setOut(new PrintStream( "/run/media/rajnvn/RAM/OUTPUT/combined/"+folder.getName()+"/"+document.getName()+".txt"));
                System.setOut(new PrintStream(String.format("G:\\OUTPUT\\combined\\%s\\%s.txt", folder.getName(), document.getName())));
                //System.out.println("------Document : "+document.getName()+"---->");
                for (File doc : Objects.requireNonNull(document.listFiles())) {
                    //System.out.println("------Doc : "+doc.getName()+"---->");
                    switch (doc.getName()) {
                        case "text.txt":
                            Scanner scanner = new Scanner(doc);
                            while (scanner.hasNextLine())
                                System.out.println(scanner.nextLine());
                            break;
                        case "html.html":
                            parseHtml(doc);
                            break;
                        case "repeat.txt":
                            parseRepeat(doc);
                    }
                }
            }
        }
    }


    public static void postProcess(File file, String path) throws IOException {
        Scanner input = new Scanner(file);
        BufferedWriter output = new BufferedWriter(new FileWriter(path));
        while (input.hasNextLine()) {
            String line = input.nextLine();
            if (line.startsWith("----"))
                continue;
            line = line.replaceAll("(http|ftp|https)://([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?","");
            line = line.replaceAll("\\[.*]", "");
            //first step remove "[alkdjflsdg]" things
            try {
                while (line.contains("[") && line.contains("]"))
                    line = line.substring(0, line.indexOf("[")) + line.substring(line.indexOf("]") + 1);
            } catch (OutOfMemoryError e) {
                System.out.println("\n\n\n\n------------------- ERROR ---------------\n" + line);
                continue;
            }

            //second step remove numericals and |
            line = line.replaceAll("[^A-Za-z\\s\n,\\.]", "");
            //third step remove "(adfh)" things
            while (line.contains("(") && line.contains(")"))
                line = line.substring(0, line.indexOf("(")) + line.substring(line.indexOf(")") + 1);
            //fourth step remove ".....") things
            line = line.replaceAll("\\.{2,}", "");
            line = line.replaceAll("www\\..*\\s","");
            line = line.toLowerCase();
            //System.out.println(line.trim());
            output.write(line.trim() + "\n");
        }
        output.close();
    }

/*for sentences extraction
    public static void parseList(File file) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory docume   ntBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);
        document.getDocumentElement().normalize();

        //System.out.println("root--> " + document.getDocumentElement().getElementsByTagName("document"));
        NodeList sentences = Objects.requireNonNull(document.getDocumentElement().getElementsByTagName("Sentences"));
        String xml="";
        for(int i=0;i<sentences.getLength();i++) {
            //System.out.println(sentences.item(i).getNodeValue());
            if(!(sentences.item(i).getChildNodes().getLength()<=0))
                //String xml=sentences.item(i).getNodeValue();
                //parseLoopX(sentences.item(i));
                xml=sentences.item(i).getChildNodes().item(0).getNodeValue();
        }
        InputSource is=new InputSource();
        is.setCharacterStream(new StringReader(xml));
        Document doc=documentBuilder.parse(is);
        System.out.println("fdg "+doc);
        parseLoopX(doc);
    }

    public static void parseSentence(String sentence)
    {

    }
    static int k=0;
    public static void parseLoopX(Node node) {
        k++;
        String str_pad = "";
        for (int i = 0; i < k; i++) {
            str_pad += "\t";
        }

        if (node.getNodeType() == Node.ELEMENT_NODE) {
            System.out.println(str_pad + node.getNodeName() + " --> Element;");
            NodeList nodeList = node.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++)
                parseLoopX(nodeList.item(i));
        } else
            System.out.println(str_pad + node.getNodeName() + " --> Node;\n" + str_pad + node.getNodeValue());
        k--;
    }*/
}
