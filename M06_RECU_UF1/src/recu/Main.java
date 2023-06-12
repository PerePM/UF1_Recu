package recu;

import recu.ArticlesCompra;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        List<ArticlesCompra> llistaCompra = capturarLlistaCompra();
        generarXML(llistaCompra, "llista_compra.xml");
        mostrarLlistaCompra(llistaCompra);
        escriureObjecteSerialitzat(llistaCompra,"llista_compra_uf1.ser");
    }

    private static List<ArticlesCompra> capturarLlistaCompra() {
        List<ArticlesCompra> llistaCompra = new ArrayList<>();

        while (true) {
            System.out.println("Captura d'articles de la llista de la compra:");
            ArticlesCompra article = capturarArticle();
            llistaCompra.add(article);

            System.out.println("Vols afegir un altre article? (s/n)");
            String resposta = llegirLinia();

            if (!resposta.equalsIgnoreCase("s")) {
                System.out.println("-----------------");
                break;
            }
        }

        return llistaCompra;
    }

    private static ArticlesCompra capturarArticle() {
        String descripcio = llegirString("Descripció: ");
        double quantitat = llegirDouble("Quantitat: ");
        String unitat = llegirString("Unitat: ");
        String seccio = llegirString("Secció: ");
        double preu = llegirDouble("Preu: ");

        return new ArticlesCompra(descripcio, quantitat, unitat, seccio, preu);
    }

    private static void generarXML(List<ArticlesCompra> llistaCompra, String nomFitxer) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // Crear XML //hqmel
            Document doc = dBuilder.newDocument();

            // Element arrel <llista_compra>
            Element arrelElement = doc.createElement("llista_compra");
            doc.appendChild(arrelElement);

            // Afegir els articles a l'arrel
            for (ArticlesCompra article : llistaCompra) {
                Element articleElement = doc.createElement("article");
                arrelElement.appendChild(articleElement);

                Element descripcioElement = doc.createElement("descripcio");
                Text descripcioText = doc.createTextNode(article.getDescripcio());
                descripcioElement.appendChild(descripcioText);
                articleElement.appendChild(descripcioElement);

                Element quantitatElement = doc.createElement("quantitat");
                Text quantitatText = doc.createTextNode(String.valueOf(article.getQuantitat()));
                quantitatElement.appendChild(quantitatText);
                articleElement.appendChild(quantitatElement);

                Element unitatElement = doc.createElement("unitat");
                Text unitatText = doc.createTextNode(article.getUnitat());
                unitatElement.appendChild(unitatText);
                articleElement.appendChild(unitatElement);

                Element seccioElement = doc.createElement("seccio");
                Text seccioText = doc.createTextNode(article.getSeccio());
                seccioElement.appendChild(seccioText);
                articleElement.appendChild(seccioElement);

                Element preuElement = doc.createElement("preu");
                Text preuText = doc.createTextNode(String.valueOf(article.getPreu()));
                preuElement.appendChild(preuText);
                articleElement.appendChild(preuElement);
            }

            // Guardar el document XML en un fitxer
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(nomFitxer));

            transformer.transform(source, result);
            System.out.println("L'arxiu XML s'ha generat correctament: " + nomFitxer);
        } catch (ParserConfigurationException | TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    private static void mostrarLlistaCompra(List<ArticlesCompra> llistaCompra) {
        System.out.println("Llista de la compra:");
        for (ArticlesCompra article : llistaCompra) {
            System.out.println("Descripció: " + article.getDescripcio());
            System.out.println("Quantitat: " + article.getQuantitat());
            System.out.println("Unitat: " + article.getUnitat());
            System.out.println("Secció: " + article.getSeccio());
            System.out.println("Preu: " + article.getPreu());
            System.out.println("------------------------");
        }
    }

    private static String llegirString(String missatge) {
        System.out.print(missatge);
        return llegirLinia();
    }

    private static double llegirDouble(String missatge) {
        while (true) {
            try {
                System.out.print(missatge);
                String input = llegirLinia();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Siusplau, introdueix un valor numèric. Recorda que els decimals s'escriuen amb un punt (.).");
            }
        }
    }

    private static String llegirLinia() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
    private static void escriureObjecteSerialitzat(List<ArticlesCompra> llistaCompra, String nomFitxer) {
        try (FileOutputStream fileOut = new FileOutputStream(nomFitxer);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

            // Escriu l'objecte serialitzat
            objectOut.writeObject(llistaCompra);
            
            System.out.println("L'objecte serialitzat s'ha escrit correctament: " + nomFitxer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
