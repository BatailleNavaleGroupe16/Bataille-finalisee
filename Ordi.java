public class Ordi{
    
    
    private byte[][] coups;
    private byte[][] dirAleat = { {0,-1}, {0,1}, {-1,0}, {1,0} };
    private byte[][] direction = { {0,-1}, {0,1}, {-1,0}, {1,0} };
    private byte aleat;
    private boolean attaque;
    private short i;
    private byte ligne;
    private byte colonne;
    private byte caseL;
    private byte caseC;
    
    public Ordi(byte[][] grille){
        this.coups = grille;
    }
    
    
    /**
     * Méthode pour l'attaque de l'ordinateur
     * Prend le joueur actif en paramètre
     * Modifie Joueur mais ne retourne rien
     */
    public void AttaqueOrdi(Joueur joueur){
        
        i = 0;
        attaque = false;
        
        while ((attaque==false) && (i<coups.length*coups[0].length)){
            
            
            //On parcourt les cases touchées de la grille et on détermine la meilleure façon d'attaquer à proximité
            
            
            if (coups[i/12][i%12]==-2){
                
                ligne = (byte)(i/12);
                colonne = (byte)(i%12);
                
                
                //Permet de connaître les coordonnées de la dernière case touchée visitée. (utile en cas de bateaux collés)
                
                
                if ((coups[ligne+1][colonne]!=-2) && (coups[ligne-1][colonne]!=-2) && (coups[ligne][colonne+1]!=-2) && (coups[ligne][colonne-1]!=-2)){//si la case est isolée
                    //Attaque aléatoire sur une case autour
                    this.AttaqueRandom();
                    
                }else{
                    
                    byte k = 0;
                    
                    while ((attaque==false) && (k<direction.length)){
                        if ((coups[ ligne+direction[k][0] ][colonne+direction[k][1]]==-2) && (coups[ligne-direction[k][0]][colonne-direction[k][1]]==0)){
                            //Vérifie les cases avant et après pour déterminer la direction la plus probable du bateau
                            
                            caseL = (byte)(ligne-direction[k][0]);
                            caseC = (byte)(colonne-direction[k][1]);
                            attaque = true;
                            
                        }
                        
                        k+=1;
                    }
                }
                
                //Si toutes les cases autour ont déjà été attaquées, on ne fait rien
                 
            }
            
            
            i +=1;
        }
        
        
        if((attaque==false) && this.coups[this.ligne][this.colonne]==-2){//Si on a bien trouvé une case touchée mais qu'on a pas pu attaquer (si bateaux collés)
            //Attaque aléatoire autour
            AttaqueRandom();
        }
        
        System.out.println("attaque " + attaque);
        //Si aucune attaque n'a pu être menée, c'est qu'il n'y a plus de case touchée. On repasse en mode chasse : Attaque aléatoire sur cases paires.
        if (attaque==false){
            do{
                //Tirage d'une case paire
                this.ligne = (byte)(10*Math.random() +1);
                this.colonne = (byte)(2*(byte)(5*Math.random()) + this.ligne%2 +1);//pour n'attaquer qu'une case sur 2
                System.out.print(this.ligne + " ligne colonne " + this.colonne);
            }while(this.coups[this.ligne][this.colonne] != 0);
            
            this.caseL = this.ligne;
            this.caseC = this.colonne;
            //this.caseC=2;
            //this.caseL=2;
            
        }
        
        joueur.caseL = this.caseL;
        joueur.caseC = this.caseC;
          
        
    }
    
    
    /**
     * Méthode pour attaquer aléatoirement autour de la case
     * Ne prend pas de paramètre
     * Ne retourne rien
     */
    private void AttaqueRandom(){
        do{
            //Tirage de la case aléatoire pour 4 cases potentielles
            aleat = (byte)(4*Math.random());
            caseL = (byte)(ligne + dirAleat[aleat][0]);
            caseC = (byte)(colonne + dirAleat[aleat][1]);
        }while(coups[caseL][caseC]!=0);//case visée déjà jouée
        attaque = true;
        
    }
     
    
}
