import java.util.Scanner;
public class Joueur{
    /*public byte [][] bateaux={
        {-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4},
        {-4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-4},
        {-4, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0,-4},
        {-4, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0,-4},
        {-4, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0,-4},
        {-4, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0,-4},
        {-4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-4},
        {-4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-4},
        {-4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-4},
        {-4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-4},
        {-4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-4},
        {-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4}
    };*/
    //public byte nbBateaux = 1;
    
    
    public byte [][] bateaux;
    public byte type;
    public byte [][] coupsPrecedents;
    public byte [] nbreCasesBateau = {2, 3, 3, 4, 5};
    public byte nbBateaux = 5;
    private byte ligne;
    private byte colonne;
    public byte caseL;
    public byte caseC;
    private byte touche;
    private Ordi ordi;
    private Bateau bateau;
    Scanner sc = new Scanner(System.in);
    
    
    
    public Joueur(byte type) {
        
        this.type = type; 
        
        //1 pour un joueur humain qui cache ; 2 pour un humain qui cherche contre ordi ; 3 pour ordi qui cache juste ; 4 pour ordi qui joue
        /*
        1 jeu classique jvj ou contre ordi en mode attaque -> placement bateaux, tableau des coups
        2 joueur contre ordi qui cache -> pas de placement des bateaux, tableau des coups
        3 ordi qui cache juste -> placement des bateaux, pas de coups passés ni futurs
        4 ordi en mode attaque -> placement bateaux, coups passés et futurs
        */
        
        if (this.type == 1){
            this.bateaux = new byte [12][12];
            this.coupsPrecedents = new byte [12][12];
            this.initialiseGrilles();
            bateau = new Bateau(this);
            bateau.placement (this);
            
        }else if (this.type ==2){
            this.coupsPrecedents = new byte [12][12];
            this.bateaux = new byte [1][1];
            this.initialiseGrilles();
            
        }else if (this.type ==3){
            this.coupsPrecedents = new byte [1][1];
            this.bateaux = new byte [12][12];
            this.initialiseGrilles();
            bateau = new Bateau(this);
            bateau.placement(this);
            
        }else if (this.type ==4){
            this.bateaux = new byte [12][12];
            this.coupsPrecedents = new byte [12][12];
            this.initialiseGrilles();
            bateau = new Bateau(this);
            bateau.placement(this);
            ordi = new Ordi(this.coupsPrecedents);
        }
        
        
    }
    
    
    /**
     * Méthode pour la bonne création de la grille
     * Ne prend pas de paramètre
     * Ne retourne rien
     */
    public void initialiseGrilles(){
        for (byte i = 0; i<this.coupsPrecedents.length; i+=1){
            this.coupsPrecedents[i][0] = -4;
            this.coupsPrecedents[0][i] = -4;
            this.coupsPrecedents[i][this.coupsPrecedents.length-1] = -4;
            this.coupsPrecedents[this.coupsPrecedents.length-1][i] = -4;
        }
        
        for (byte i = 0; i<this.bateaux.length; i+=1){
            this.bateaux[i][0] = -4;
            this.bateaux[0][i] = -4;
            this.bateaux[i][this.bateaux.length-1] = -4;
            this.bateaux[this.bateaux.length-1][i] = -4;
        }
    }
    
    /**
     * Méthode pour déterminer la manière d'attaquer selon ordi ou joueur
     * Prends la cible en paramètre
     * 
     */
    public void Attaque(Joueur cible){
        
        if ((this.type == 1) || (this.type ==2)){
            this.AttaqueJoueur(cible);
        }else if (this.type ==4){
            this.AttaqueOrdi(cible);
        }
        
    }
    
    
    /**
     * Méthode pour le choix de la case à attaquer pour l'ordi
     * Prend la cible en paramètre
     * 
     */
    public void AttaqueOrdi(Joueur cible){
        
        this.ordi.AttaqueOrdi(this);
        
        this.AttaqueCase(cible);
        
    }
    
    
    
    /**
     * Méthode pour le choix de la case à attaquer pour humain
     * Prends la cible en paramètre
     * 
     */
    public void AttaqueJoueur(Joueur cible){
        
        //Choix de l'attaque
        
        do{
            
            //choix endroit attaque
            do{
                System.out.print("Choisissez la ligne à attaquer ");
                this.ligne = (byte)sc.nextInt();
            }while( (this.ligne<1) || (this.ligne>this.coupsPrecedents.length-2) );
            //this.ligne-=1;
            
            do{
                System.out.print("Choisissez la colonne à attaquer ");
                this.colonne = (byte)sc.nextInt();
            }while( (this.colonne<1) || (this.colonne>this.coupsPrecedents[0].length-2) );
            //this.colonne -=1;
        }while(this.coupsPrecedents[this.ligne][this.colonne] != 0);
        
        this.caseL = this.ligne;
        this.caseC = this.colonne;
        
        System.out.println("Vous attaquez la case " + (this.ligne) + " " + (this.colonne));
        this.AttaqueCase(cible);
    }
    
    
    
    /**
     * Méthode pour l'attaque de la case visée
     * Prend la cible en paramètre
     * 
     */
    public void AttaqueCase(Joueur cible){    
        
        //Gestion de l'attaque
        
        this.touche = cible.bateaux[this.caseL][this.caseC]; 
        
        System.out.println("\n\n\n\n\n");
        
        if (this.touche ==0){
            System.out.println("Loupé");
            this.coupsPrecedents[this.caseL][this.caseC] = -1;
        }else{
            System.out.println("Touché");
            cible.nbreCasesBateau[touche-1] -=1;
            this.coupsPrecedents[this.caseL][this.caseC] = -2;
            if (cible.nbreCasesBateau[touche-1] == 0){
                cible.nbBateaux -=1;
                //gestion coulé = changement des valeurs des coups précédents
                for (byte i = 0; i<cible.bateaux.length ; i +=1){
                    if (cible.bateaux[i][this.caseC] == this.touche){
                        this.coupsPrecedents[i][this.caseC] = -3;
                    }
                }
                
                for (byte j = 0; j<cible.bateaux[0].length ; j +=1){
                    if (cible.bateaux[this.caseL][j] == this.touche){
                        this.coupsPrecedents[this.caseL][j] = -3;
                    }
                }
                
                System.out.println("Coulé");
            }
            
            
        }
        
        
    }
    
    
    
    /**
     * Méthode pour l'affichage des coups joués
     * Prend le nombre de bateaux restant à l'autre en paramètre
     * 
     */
    public void afficheGrille(byte[][] tab){
        
        if (this.type!=3){
            System.out.print("      ");
            for(byte i = 1; i < tab[0].length-1 ; i+=1){
                System.out.print(i + "  ");
            }
            System.out.println("\n");
            
            for(int i = 1 ; i<tab.length-1 ; i++){
                
                if (i<10){
                    System.out.print(" ");
                }
                System.out.print(i + "   ");
                
                for(int j = 1 ; j<tab[i].length-1 ; j++){
                    
                    if(tab[i][j] == 0){
                        System.out.print(" . ");
                    }else if (tab[i][j] == -1){// || matrice[i][j] == 1 || matrice[i][j] == 2 || matrice[i][j] == 3 || matrice[i][j] == 4){
                        System.out.print(" X ");
                    }else if (tab[i][j] == -2){
                        System.out.print(" O ");
                    }else if (tab[i][j] == -3){
                        System.out.print(" C ");
                    }else if (tab[i][j] == -4){
                        System.out.print(" B ");
                    }else if (tab[i][j] > 0){
                        System.out.print(" " + tab[i][j] + " ");
                    }
                    /*else{
                        System.out.print(" E ");
                    }*/
                    
                }
                System.out.println();
            }
            //System.out.println("\n" + "Il reste " + bateauxAdv + " bateaux à trouver");
        }
    }
    
    public boolean fini(){
        return !(nbBateaux==0);
    }
    
    
    
}
