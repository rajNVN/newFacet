package preprocess;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Trail {
    //static int pad = 0;
    public static void parse(File in, File out) throws IOException {
        int i = 0;
        long start = System.nanoTime();
        for (File query : in.listFiles()) {
            ++i;
            String fname = query.getName();
            String outFname = out.getAbsolutePath() + "/" + fname.substring(0, fname.indexOf(".")) + "/listFile.txt";
            new File(outFname).createNewFile();
            BufferedWriter outf = new BufferedWriter(new FileWriter(outFname));
            System.out.println(i + " - " + outFname);
            Scanner scanner = new Scanner(query);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (Pattern.compile("<EntityList.*>").matcher(line).find()) {
                    line = line.substring(line.indexOf("[") + 1);
                    for (String subLine : line.split("]\\["))
                        outf.write(subLine + "\n");
                }
            }
            outf.close();
        }
        System.out.println("Time : " + (double) (System.nanoTime() - start) / 1000000000);
    }

    public static void parseList(File infolder, String outFolder) throws IOException {
        for (File query : infolder.listFiles()) {
            System.out.println("accesing - " + query.getAbsolutePath() + "/listFile.txt");
            Scanner input = new Scanner(new File(query.getAbsolutePath() + "/listFile.txt"));
            String fname = outFolder + "/" + query.getName() + "/listFile.txt";
            System.out.println(fname);
            new File(fname).createNewFile();
            BufferedWriter listWriter = new BufferedWriter(new FileWriter(fname));
            while (input.hasNextLine()) {
                String line = input.nextLine();
                //System.out.println(line);
                if (!line.isEmpty()) {
                    line = line.substring(1);
                    for (String subLine : line.split("\\)\\(")) {
                        try {
                            subLine = subLine.substring(subLine.indexOf(":#:") + 3);
                            listWriter.write(subLine.substring(0, subLine.indexOf(":#:")) + "\n");
                        } catch (StringIndexOutOfBoundsException e) {
                            continue;
                        }
                    }
                }
            }

        }
    }

    public static void urlMatcher(String line)
    {
        if(Pattern.compile("(http|ftp|https)://([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?").matcher(line).find())
            System.out.println("matched");
    }

    public static void parseSites(String path) throws FileNotFoundException {
        String devider = System.getProperty("file.separator");
        for (File folder : new File(path).listFiles()) {
            File listEntry = new File(folder.getAbsolutePath() + devider + "list.txt");
            //System.out.println(folder.getAbsolutePath() + System.getProperty("file.separator") + "list.txt");
            //System.out.println("outset"+devider+folder.getName()+ System.getProperty("file.separator") + "list.txt");
            System.setOut(new PrintStream("outset" + devider + folder.getName() + System.getProperty("file.separator") + "listFile.txt"));
            Scanner input = new Scanner(listEntry);
            while (input.hasNextLine()) {
                String list="";
                String line = input.nextLine();
                line = line.substring(2);
                try {
                    line = line.substring(line.indexOf(":#:") + 3);
                    list = line.substring(0, line.indexOf(":#:"));
                } catch (StringIndexOutOfBoundsException e) {
                    continue;
                }
                Pattern pattern = Pattern.compile("(:#:.*\\.com)");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String site = matcher.group();
                    //System.out.println(site);
                    String[] sit = site.split(":#:");
                    String webSite = sit[sit.length - 1];
                    System.out.println(list + "|" + webSite);
                }
            }
        }
    }

    public static void correctListsfor2(String path) throws IOException {
        String devider = System.getProperty("file.separator");
        for (File folder : new File(path).listFiles()) {
            //new File(folder.getAbsolutePath() + devider + "list.txt").createNewFile();
            System.setOut(new PrintStream(folder.getAbsolutePath() + devider + "list.txt"));
            //System.out.println("File : " + folder.getAbsolutePath() + devider + "listFile.txt");
            Scanner input = new Scanner(new File(folder.getAbsolutePath() + devider + "listFile.txt"));
            while (input.hasNextLine()) {
                String line = input.nextLine();
                Pattern pattern = Pattern.compile("(]!.*!\\[)");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    System.out.println("->"+matcher.replaceAll("\n->"));
                } else {
                    System.out.println("->"+line);
                }
            }
        }
    }
    /* for website matching
    String testCase="abc:#:me.com";
        Pattern pattern=Pattern.compile("(:#:.*.\\.com)");
        Matcher matcher=pattern.matcher(testCase);
        if(matcher.find())
        for(int i=0;i<matcher.groupCount();i++) {
            String site = matcher.group(i);
            System.out.println(site.substring(3));
        }
        System.out.println(matcher.start()+3);
     */
    /*
        public static void parseFile(File file) throws FileNotFoundException {
            long start = System.nanoTime();
            FileOutputStream fos = new FileOutputStream("/home/rajnvn/workspace/ResultFacets/htm.txt");
            System.setOut(new PrintStream(fos));
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.contains("Repeat"))
                    System.out.println(line);
            }
            System.out.println("\n\n\n TIME : " + (double) (System.nanoTime() - start) / 1000000000);
        }

        public static void parseFile1(File file) throws FileNotFoundException {
            long start = System.nanoTime();
            FileOutputStream fos = new FileOutputStream("/home/rajnvn/workspace/ResultFacets/free_text.txt");
            System.setOut(new PrintStream(fos));
            Scanner scn = new Scanner(file);
    z        while (scn.hasNextLine()) {
                String line = scn.nextLine();
                line = line.replaceAll("#|;|:", "");
                for (String str : line.split(":|-"))
                    //for(String string:str.split("-"))
                    System.out.println(str);
            }
        }
    */
    public static void parseXML(File file) throws IOException, SAXException, ParserConfigurationException {
        long start = System.nanoTime();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);
        document.getDocumentElement().normalize();

        System.out.println("root--> " + document.getDocumentElement().getElementsByTagName("document"));
        NodeList nodeList = document.getDocumentElement().getElementsByTagName("document");
        for (int i = 0; i < nodeList.getLength(); i++) {
            System.out.println(nodeList.item(i).getNodeName() + "--> " + nodeList.item(i).getNodeValue() + " *i* -->" + i);
            NodeList nodeList1 = nodeList.item(i).getChildNodes();
            for (int j = 0; j < nodeList1.getLength(); j++) {
                System.out.println(nodeList1.item(j).getNodeName() + "--> " + nodeList1.item(j).getNodeValue() + " *j* -->" + j);
                NodeList nodeList2 = nodeList1.item(j).getChildNodes();
                for (int k = 0; k < nodeList2.getLength(); k++)
                    System.out.println(nodeList2.item(k).getNodeName());
            }
        }
    }

    public static void verifyAll(File mainFolder) {
        for (File folder : mainFolder.listFiles()) {
            if (folder.listFiles().length > 100)
                for (File doc : folder.listFiles())
                    if (!doc.isDirectory())
                        if (doc.delete())
                            System.out.println("deleted");
        }
    }

    /*  static int i=0;
    public static void parseHLoop(org.jsoup.nodes.Element element) {
        String space = "";
        i++;
        for (int c = 0; c < i; c++)
            space += "   ";
        if (element.text().endsWith("23"))
            System.out.println("entred"+space + element.text() + "\n");
        else {
           // System.out.println(i"");
        if(element.tagName().equals("p")||element.tagName().equals("td")||element.tagName().equals("td"))
        {
            System.out.println(element.tagName()+"-----"+element.text());
        }
        else
        {
            for (org.jsoup.nodes.Element element1 : element.children()) {
                /*List<TextNode> textNode=element.textNodes();
                for (TextNode node : textNode) {
                    if(!node.isBlank())
                        System.out.println(node.getWholeText());
                parseHLoop(element1);
            }
        }1
        i--;
    }
*/
}
