import java.util.Scanner;

public class BatailleNavale {
    
    public static void main(String[] args){
        
        Scanner sc = new Scanner(System.in);
        
        
        //Initialisation : choix du mode jeu : JVJ, JVO
        
        byte typeJeu = 0;
        byte typeMax = 3;
        
        do{
            System.out.println("Recherche des bateaux de l'ordinateur : 1 - Joueur contre Joueur : 2 - Joueur contre Ordinateur : 3");
            System.out.print("Votre choix : ");
            typeJeu = (byte)sc.nextInt();
            
        }while ((typeJeu <=0) || (typeJeu > typeMax));
        
        
        //Création des joueurs
        
        Joueur j1;
        Joueur j2;
        
        Joueur[] tabJ = new Joueur[2];
        
        /*
        si 1 => j1(2) j2(3)
        si 2 => j1(1) j2(1)
        si 3 => j1(1) j2(4)
        */
        
        if (typeJeu == 1){
            j1 = new Joueur((byte)2);
            j2 = new Joueur((byte)3);
        }else if(typeJeu == 2){
            j1 = new Joueur((byte)1);
            j2 = new Joueur((byte)1);
        }else{
            j1 = new Joueur((byte)1);
            j2 = new Joueur((byte)4);
        }
        
        tabJ[0] = j1;
        tabJ[1] = j2;
        
        
        
        
        /*boucle du jeu : Affichage des coups joués, nb bateaux restants à adversaire.
        choix case à attaquer : check si déjà attaquée ou pas
        */
        
        byte actuel = 1; // pour commencer par joueur 1 dans boucle
        byte autre = (byte)((actuel + 1)%2);
        boolean enCours = true;
        
        while (enCours){
            
            actuel = (byte)((actuel + 1)%2);
            autre = (byte)((actuel + 1)%2);
            
            System.out.println("Joueur " + (actuel+1));
            
            //Affichage bateau
            tabJ[actuel].afficheGrille(tabJ[actuel].coupsPrecedents);
            
            //Affichage nb bateaux restants
            System.out.println("Il vous reste " + tabJ[autre].nbBateaux + " bateaux à trouver");
            tabJ[actuel].Attaque(tabJ[autre]);
            
            
            System.out.println("Tour joué\n");
            
            enCours = tabJ[autre].fini();
            
            
        }
        
        System.out.println("Le joueur " + (actuel+1) + " a gagné cette partie");
        
        
        tabJ[actuel].afficheGrille(tabJ[actuel].coupsPrecedents);
        
        
    }
    
      
}
