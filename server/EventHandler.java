/**
 * L'interface fonctionnelle EventHandler définit la méthode qui traite les événements.
 */

@FunctionalInterface
public interface EventHandler {
    /**
     * La méthode handle de l'interface fonctionnelle représente un traitement d'événement selon la commande envoyée et son argument.
     * @param cmd: Commande envoyée au serveur
     * @param arg: Argument de la commande envoyée
     */
    void handle(String cmd, String arg);
}
