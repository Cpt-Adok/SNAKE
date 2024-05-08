package Environnements;

import Item.Effects;
import Item.Items;
import java.util.Random;

public class Fruits{
    String typeFruit;
    Effects bonus;

    public Fruits(){
        Random r=new Random();
        Items [] fruits=new Items[] {Items.BANANE,Items.FRAISE,Items.ORANGE};
        int q=r.nextInt(3);
        this.typeFruit=fruits[q].getName();
        this.bonus=fruits[q].getEffects();
    }

    public void getFruit(){
        System.out.println(this.typeFruit);
    }

    public void getBonus(){
        System.out.println(this.bonus);
    }
}