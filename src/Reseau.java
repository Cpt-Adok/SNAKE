import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.net.ssl.HttpsURLConnection;

import static java.util.Arrays.copyOfRange;

/**
 * <p> Cette classe est destiné un projet de fin d'année de L1, ne pas l'utiliser dans 
 * d'autre circonstance que dans ce projet. Elle contient plusieurs fonctions qui est 
 * utile pour la communication entre la machine et le site Padifac.
 * 
 * @author Loïc GUEZO
 * @version 1.0.0
 * @since 2024
 * @see <a href="https://cervelle.pages.lacl.fr/padiflac/test_ocaml2.html"> Padiflac </a>
 */
@SuppressWarnings("deprecation")
 public class Reseau {
    private static final String ADDRESS = "https://prog-reseau-m1.lacl.fr/padiflac/";
    private String CHANNEL;
    private URL url;

    private ArrayList<String> wordArrayList;
    private ArrayList<String> newerWordArrayList;

    private int index;

    /** 
     * <p>Le constructeur defini quelque information cruciale puis appelle directement la 
     * fonction {@code #searchContent()}.
     * 
     * @param channel Ce string est utilisé pour se connecter à un channel dans le serveur web
    */
    public Reseau(String channel) {
        this.CHANNEL = channel;
        this.wordArrayList = new ArrayList<String>();
        this.newerWordArrayList = new ArrayList<String>();

        this.connexion();
    }

    /** 
     * <p>cette fonction redefini quelque information cruciale puis appelle directement la 
     * fonction {@code #searchContentSorted()}.
     * 
     * @param channel Ce string est utilisé pour se reconnecter à un channel dans le serveur web
    */
    public void reconnexion(String channel) {
        this.CHANNEL = channel;
        this.wordArrayList.clear();
        this.newerWordArrayList.clear();

        this.connexion();
    }

    /**
     * @return cette fonction renvoie tout ce qui est disponible sur le site quand on appelle la 
     * fonction searchContent et qui mets tout le texte dans le {@link ArrayList} 
     */
    public ArrayList<String> getArrayContent() {
        return this.wordArrayList;
    }

    /**
     * @return cette fonction renvoie uniquement les nouveauté de qui est disponible sur le site 
     * quand on appelle la fonction searchContent et qui mets tout le texte dans le {@link ArrayList} 
     */
    public ArrayList<String> getNewArrayListContent() {
        return this.newerWordArrayList;
    }

    /**
     * <p> cette fonction est en parallèle avec {@code #getLastedContent()} qui récupère le dernier indice 
     * utilisé lors de l'appelle de {@code #getLastedContent()}.
     * @return renvoie le dernier indice
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * <p> cette fonction est en parallèle avec {@code #getLastedContent()} et {@code #getIndex()}. Elle va
     * reset l'indice.
     */
    public void resetIndex() {
        this.index = 0;
    }

    /** 
     * @return cette fonction renvoie le String de l'indice puis l'incrémenter. Si c'est la fin de la liste, 
     * il renvoie null sans incrémenter  
     */
    public String getLastedContent() {
        return (this.getArrayContent().size() > this.index) ? getArrayContent().get(this.index++) : null;
    }

    /**
     * <p>cherche les informations et trie seulement les nouvelles entre eux :
     * <pre><code>
     * Reseau reseau = new Reseau("ChatTest"); // dans le serveur : {"arbre", "pomme", "chocolat", "arbre"}
     *System.out.println(reseau.searchContentSorted().toString()); // {"arbre", "arbre", "pomme", "chocolat"}
     * </code></pre>
     * @return il renvoie le {@link ArrayList} de la liste
     */
    public ArrayList<String> searchContentSorted() {
        ArrayList<String> localNewerArrayList = this.searchContent();
        if (this.getArrayContent().isEmpty()) this.addContentToContent(localNewerArrayList);
        else this.searchDuplicates(localNewerArrayList);
        return this.wordArrayList;
    }

    /**
     * <p> cherche les informations mais ne trie pas les nouvelles entre eux :
     * <pre><code>
     * Reseau reseau = new Reseau("ChatTest"); // dans le serveur : {"arbre", "pomme", "chocolat", "arbre"}
     *System.out.println(reseau.searchContentSorted().toString()); // {"arbre", "arbre", "pomme", "chocolat"}
     * </code></pre>
     * <p> attention, il peut y avoir des erreurs en rajoutant par exemple {"arbre", "pomme", "chocolat", "pomme"}
     * @return
    */
    public ArrayList<String> searchArrayListNotSorted() {
        ArrayList<String> localNewerArrayList = this.searchContent();
        this.addContentToContent(localNewerArrayList);
        return this.wordArrayList;
    }

    /**
     * <p> ce programme essaye d'envoyer le String en parametre. Attention, la communication entre le serveur et la machine
     * peut prendre du temps !
     * @param content Le contenu de texte que vous voulez renvoyer
     */
    public void sendContent(String content) {
        try {
            this.url = new URL(ADDRESS + CHANNEL + "?nonce=" + this.generateNonce());
            HttpsURLConnection connection = (HttpsURLConnection)this.url.openConnection();
            
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
        
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(serializeToString(content));
            outputStream.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                inputStream.close();
            } else {
                System.out.println("Erreur lors de l'envoi de la requête. Code de réponse : " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> searchContent() {
        try {
            byte[] bytes = this.url.openConnection().getInputStream().readAllBytes();
            ArrayList<String> localNewerArrayList = new ArrayList<String>();
            
            StringBuffer textContent = new StringBuffer();

            for(int i = 0; i < bytes.length; i++) {
                byte charByte = bytes[i];

                if(charByte == '|' && textContent.length() > 0) {
                    int size = Integer.parseInt(textContent.toString());
                    byte[] buffer = copyOfRange(bytes, i + 1, i + 1 + size);

                    localNewerArrayList.add(new String(buffer));

                    i += size;
                    textContent.setLength(0);
                    
                } else {
                    textContent.append((char)charByte);
                }
            }    
            return localNewerArrayList;

        } catch (Exception e) {
            return null;
        }
    }

    private void addContentToContent(ArrayList<String> localArrayList) {
        int arrayListLength = this.getArrayContent().size();

        this.newerWordArrayList.clear();

        for(int i = arrayListLength; i < localArrayList.size(); i++) {
            String arrayListValue = localArrayList.get(i);
            this.wordArrayList.add(arrayListValue);
            this.newerWordArrayList.add(arrayListValue);
        }
    }

    private void searchDuplicates(ArrayList<String> localArrayList) {
        HashMap<String, Integer> counterHashMap = this.compareHashMap(
            arrayListToHashmapCounter(this.wordArrayList),
            arrayListToHashmapCounter(localArrayList)
        );

        this.newerWordArrayList.clear();

        for(Map.Entry<String, Integer> value : counterHashMap.entrySet()) {
            String arrayListValue = value.getKey();

            for(int i =0; i<value.getValue(); i++) {
                this.wordArrayList.add(arrayListValue);
                this.newerWordArrayList.add(arrayListValue);
            }
        }
    }

    private HashMap<String, Integer> arrayListToHashmapCounter(ArrayList<String> list) {
        HashMap<String, Integer> hashmapListCounter = new HashMap<String, Integer>();

        for(int i = 0; i<list.size(); i++) {
            String key = list.get(i);
            Integer value = 0;

            if(hashmapListCounter.containsKey(key)) {
                value = Integer.sum(hashmapListCounter.get(key), 1);
            } else {
                value = 1;
            }

            hashmapListCounter.put(key, value);
        }

        return hashmapListCounter;
    }

    private HashMap<String, Integer> compareHashMap(HashMap<String, Integer> olderHashMap, HashMap<String, Integer> newerHashMap) {
        HashMap<String, Integer> hashMapCompared = new HashMap<String, Integer>();

        for (Map.Entry<String, Integer> entry : newerHashMap.entrySet()) {
            String key = entry.getKey();
            Integer newValue = entry.getValue();
            Integer oldValue = olderHashMap.get(key);

            if (oldValue == null) {
                hashMapCompared.put(key, newValue);
            } else if (newValue > oldValue) {
                hashMapCompared.put(key, newValue - oldValue);
            }
        }

        return hashMapCompared;
    }

    private String generateNonce() {
        return UUID.randomUUID().toString().replace("-", "")+
        UUID.randomUUID().toString().replace("-", "");
    }

    private byte[] serializeToString(String text) throws IOException {
        return text.getBytes();
    }

    private void connexion() {
        try {
            this.url = new URL(ADDRESS + CHANNEL);

            this.searchContentSorted();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}