import java.util.Scanner;

public class Bateau {
	
    Scanner sc = new Scanner(System.in);
    public byte taille;
    public byte debutL;
    public byte debutC;
    public byte numero;
    public byte[][] grille;
    public byte estVertical; //1 pour vertical 2 pour horizontal
    
    public Bateau (Joueur joueur) {
        this.grille = joueur.bateaux;
    }
    
    
    public void placement (Joueur joueur){
        for (numero = 1; numero <= joueur.nbreCasesBateau.length; numero+=1){
            
            //Affichage bateaux
            
            this.taille = joueur.nbreCasesBateau[numero-1];
            do{
                
                if (joueur.type == 1){
                    joueur.afficheGrille(this.grille);
                    this.placementJoueur(); 
                    
                }else{
                    this.placementOrdi();
                }
                System.out.println("placement");
                
            }while(!this.verifPlace());//à revoir
            
            this.placer();
            
            System.out.println("placé");
            
            
        }
        joueur.afficheGrille(joueur.bateaux);
    }
    
    
    /**
     * Méthode pour le placement des bateaux pour le joueur
     * Modifie directement la grille du joueur
     * 
     */
    public void placementJoueur () { // les infos seront directement rentrées par le joueur
        
        do{
            System.out.println("Veuillez saisir la ligne de la position la plus à gauche et la plus en haut de votre bateau n° : "+(numero) + " de taille " + this.taille);
            this.debutL = (byte)sc.nextInt();
        }while(debutL < 1 || debutL > 10);//doit être dans la grille
        
        do{
            System.out.println("Veuillez saisir la colonne de la position la plus à gauche et la plus en haut de votre bateau n° : "+(numero) + " de taille " + this.taille);
            this.debutC = (byte)sc.nextInt();
        }while(debutC < 1 || debutC > 10);//doit être dans la grille
        
        do{
            System.out.println("Veuillez saisir l'orientation de votre bateau : VERTICAL : 1 / HORIZONTAL : 2 ");
            this.estVertical = (byte)sc.nextInt();
        }while(this.estVertical != 1 && this.estVertical != 2);
                
    }
    
    
    /**
     * Méthode pour le placement des bateaux de l'ordi
     * 
     * 
     */
    public void placementOrdi () {
        
        this.debutL = (byte)((this.grille.length-2)*Math.random()+1);
        this.debutC = (byte)((this.grille[0].length-2)*Math.random()+1);
        this.estVertical = (byte)(2*Math.random()+1);
        System.out.println("debL"+debutL+" debC"+debutC+" vert"+estVertical);
        
    }
    
    
	public boolean verifPlace(){//a refaire
        byte i = 0;
        boolean bool = true;//rester true tant que le placement est bon
        
        if(estVertical == 1){
            while(i < this.taille /*&& this.debutH - i < 0*/ && bool){//positionnement entre les bornes de la taille du bateau, vérif si bien dans le tableau de la grille
                bool = (this.grille[this.debutL+i][this.debutC] == 0);
                i++;
            }
            
        }else{
            while(i < this.taille /*&& this.debutH - i < 0*/ && bool){
                bool = (this.grille[this.debutL][this.debutC+i] == 0);
                i++;
            }
        }
        return bool;
    }
    
    public void placer () {
        if (this.estVertical == 1) {
            for (byte i = 0; i < this.taille; i++) {
                this.grille[this.debutL+i][this.debutC] = this.numero;
            }
        } else {
            for (byte i = 0; i < this.taille; i++) {
                this.grille[this.debutL][this.debutC+i] = this.numero;
            }
        }
    }
    
    
    
    
    
    
}
