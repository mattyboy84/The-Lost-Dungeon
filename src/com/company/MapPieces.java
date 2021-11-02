package com.company;


import javafx.scene.image.ImageView;

public class MapPieces extends Base_Class{//WIDTH: 72 HEIGHT: 64

    public MapPieces(int i, int j, int room) {
        this.layoutX = (72 * i);
        this.layoutY = (64 * j);
        this.room = room;
        switch (room) {
            case 1:
                this.imageViewIMG = new ImageView("file:src\\game\\Room.png");
                break;
            case 2:
                this.imageViewIMG = new ImageView("file:src\\game\\ShopRoom.png");
                break;
            case 3:
                this.imageViewIMG = new ImageView("file:src\\game\\BossRoom.png");
                break;
        }
    }
}
//    public void thing(ArrayList<Enemy> enemies, ArrayList<Magic> magic, ArrayList<Fireballs> fireballs,Label score, Group group,TranslateTransition translate,ImageView boss,Image link_sword_down,Image  link_sword_left, Image link_sword_right,Image  link_sword_up,Image  link_staff_down,Image  link_staff_left, Image link_staff_right, Image link_staff_up,Rectangle sword_down,Rectangle  sword_left, Rectangle sword_right, Rectangle sword_up,Rectangle  usedSword,Image bossDoor_closed,Image bossDoor_open,ImageView bossDoor,ImageView bossHealthBar,Rectangle bossHealthBarREC,Label coinNumber, Image link_running_down_1, Image link_running_left_1,Image  link_running_right_1, Image link_running_up_1,ImageView hero,Image  link_running_down_2,Image  link_running_left_2,Image  link_running_right_2, Image link_running_up_2){