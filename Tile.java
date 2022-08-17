package proceduralTesting;

import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;
 
public class Tile {
	private int tileNum = -2;
	private boolean leftSlot = false;//1
	private boolean topSlot = false;//2
	private boolean rightSlot = false;//3
	private boolean bottomSlot = false;//4
	//tileOptions is a list of options, it will get reduced as tile options are removed
	private ArrayList tileOptions = new ArrayList();
	private boolean infected = false;
	
	public boolean infected2 = false;
	
//	private int value =  0;
	
	Random rng;
	
	//so this should be able to set the tile type, return it, and wipe the tile from being used again by that object
	
	public Tile(/*Random rng*/) {
//		this.rng = rng;
		for(int i = 0; i < 15; i++) {
			tileOptions.add(i);
		}
	}
	
//	public int getValue() {
//		return value;
//	}
//	public void setValue(int value) {
//		this.value = value;
//	}
	
	public void setSlot(/*int slot, int tileType*/) {
		switch(tileNum) {
			case 0:
//				System.out.println("\tCase 0");
				leftSlot = true;
				topSlot = true;
				rightSlot = true;
				bottomSlot = true;
//				System.out.println("\tEnd Case 0");
				//System.out.println("\tTileNum - " + tileNum + "\n\t\tLeftSlot - " + leftSlot + "\n\t\tTopSlot - " + topSlot + "\n\t\tRightSlot - " + rightSlot + "\n\t\tBottomSlot - " + bottomSlot);
				break;
			case 1:
				leftSlot = true;
				topSlot = true;
				rightSlot = false;
				bottomSlot = true;
				break;
			case 2:
				leftSlot = true;
				topSlot = false;
				rightSlot = true;
				bottomSlot = true;
				break;
			case 3:
				leftSlot = false;
				topSlot = true;
				rightSlot = true;
				bottomSlot = true;
				break;
			case 4:
				leftSlot = true;
				topSlot = true;
				rightSlot = true;
				bottomSlot = false;
				break;
			case 5:
				leftSlot = true;
				topSlot = false;
				rightSlot = true;
				bottomSlot = false;
				break;
			case 6:
				leftSlot = false;
				topSlot = true;
				rightSlot = false;
				bottomSlot = true;
				//System.out.println("\tTileNum - " + tileNum + "\n\t\tLeftSlot - " + leftSlot + "\n\t\tTopSlot - " + topSlot + "\n\t\tRightSlot - " + rightSlot + "\n\t\tBottomSlot - " + bottomSlot);
				break;
			case 7:
				leftSlot = true;
				topSlot = false;
				rightSlot = false;
				bottomSlot = true;
				break;
			case 8:
				leftSlot = false;
				topSlot = false;
				rightSlot = true;
				bottomSlot = true;
				break;
			case 9:
				leftSlot = false;
				topSlot = true;
				rightSlot = true;
				bottomSlot = false;
				break;
			case 10:
				leftSlot = true;
				topSlot = true;
				rightSlot = false;
				bottomSlot = false;
				break;
			case 11:
				leftSlot = false;
				topSlot = true;
				rightSlot = false;
				bottomSlot = false;
				break;
			case 12:
				leftSlot = false;
				topSlot = false;
				rightSlot = true;
				bottomSlot = false;
				break;
			case 13:
				leftSlot = false;
				topSlot = false;
				rightSlot = false;
				bottomSlot = true;
				break;
			case 14:
				leftSlot = true;
				topSlot = false;
				rightSlot = false;
				bottomSlot = false;
				break;
		}
	}
	
	public void setTile() {
		//System.out.println("-----Start setSlot");
		boolean[] tileSet = {leftSlot, topSlot, rightSlot, bottomSlot};
		int[] intTileSet = {0,0,0,0};
		//String idk = "";
		//String idk2 = "";
		for(int i = 0; i < tileSet.length; i++) {
			//idk += tileSet[i] + ", ";
			if(tileSet[i] == false) {
				intTileSet[i] = -1;
			} else if(tileSet[i] == true) {
				intTileSet[i] = 1;
			}
			//idk2 += intTileSet[i] + ", ";
		}
		//System.out.println("Tile's Sides - " + idk);
		//System.out.println("IntTileSet - " + idk2);
		
		//can I shorten this?
//		boolean[] tile0 = {true, true, true, true};
//		boolean[] tile1 = {true, true, false, true};
//		boolean[] tile2 = {true, false, true, true};
//		boolean[] tile3 = {false, true, true, true};
//		boolean[] tile4 = {true, true, true, false};
		
//		boolean[] tile5 = {true, false, true, false};
//		boolean[] tile6 = {false, true, false, true};
//		boolean[] tile7 = {true, false, false, true};
//		boolean[] tile8 = {false, false, true, true};
//		boolean[] tile9 = {false, true, true, false};
//		boolean[] tile10 = {true, true, false, false};
		
//		boolean[] tile11 = {false, true, false, false};
//		boolean[] tile12 = {false, false, true, false};
//		boolean[] tile13 = {false, false, false, true};
//		boolean[] tile14 = {true, false, false, false};
		
		int[] tile0 = {1,1,1,1};
		int[] tile1 = {1,1,-1,1};
		int[] tile2 = {1,-1,1,1};
		int[] tile3 = {-1,1,1,1};
		int[] tile4 = {1,1,1,-1};
		
		int[] tile5 = {1,-1,1,-1};
		int[] tile6 = {-1,1,-1,1};
		int[] tile7 = {1,-1,-1,1};
		int[] tile8 = {-1,-1,1,1};
		int[] tile9 = {-1,1,1,-1};
		int[] tile10 = {1,1,-1,-1};
		
		int[] tile11 = {-1,1,-1,-1};
		int[] tile12 = {-1,-1,1,-1};
		int[] tile13 = {-1,-1,-1,1};
		int[] tile14 = {1,-1,-1,-1};
		
		//switch wont work with array data types
		
		if(Arrays.equals(intTileSet, tile0)) {
			this.setTileNum(0);
		}
		else if(Arrays.equals(intTileSet, tile1)) {
			this.setTileNum(1);
		}
		else if(Arrays.equals(intTileSet, tile2)) {
			this.setTileNum(2);
		}
		else if(Arrays.equals(intTileSet, tile3)) {
			this.setTileNum(3);
		}
		else if(Arrays.equals(intTileSet, tile4)) {
			this.setTileNum(4);
		}
		else if(Arrays.equals(intTileSet, tile5)) {
			this.setTileNum(5);
		}
		else if(Arrays.equals(intTileSet, tile6)) {
			this.setTileNum(6);
		}
		else if(Arrays.equals(intTileSet, tile7)) {
			this.setTileNum(7);
		}
		else if(Arrays.equals(intTileSet, tile8)) {
			this.setTileNum(8);
		}
		else if(Arrays.equals(intTileSet, tile9)) {
			this.setTileNum(9);
		}
		else if(Arrays.equals(intTileSet, tile10)) {
			this.setTileNum(10);
		}
		else if(Arrays.equals(intTileSet, tile11)) {
			//System.out.println("Tile 11 Used");
			this.setTileNum(11);
		}
		else if(Arrays.equals(intTileSet, tile12)) {
			this.setTileNum(12);
		}
		else if(Arrays.equals(intTileSet, tile13)) {
			this.setTileNum(13);
		}
		else if(Arrays.equals(intTileSet, tile14)) {
			this.setTileNum(14);
		} else {
			this.setTileNum(-1);
		}
		//System.out.println("TileNum for setTile - " + tileNum);
	}
	
	public void setTileNum(Random rng) {
		tileNum = rng.nextInt(tileOptions.size());
		setSlot();
	}
	public void setTileNum(int num) {
		tileNum = num;
		setSlot();
	}
	
	public int getTileNum() {
		return tileNum;
	}
	
	public boolean getTopSlot() {
		return topSlot;
	}
	public boolean getBottomSlot() {
		return bottomSlot;
	}
	public boolean getLeftSlot() {
		return leftSlot;
	}
	public boolean getRightSlot() {
		return rightSlot;
	}
	
	public void setTopSlot(boolean slot) {
		topSlot = slot;
	}
	public void setBottomSlot(boolean slot) {
		bottomSlot = slot;
	}
	public void setLeftSlot(boolean slot) {
		leftSlot = slot;
	}
	public void setRightSlot(boolean slot) {
		rightSlot = slot;
	}
	

	
	public void invalidTileNum(Random rng) {
		tileOptions.remove(tileNum);
		setTileNum(rng);
	}
	
	public void invalidTileNum(int num) {
		tileOptions.remove(tileNum);
		tileNum = num;
	}
	
	public boolean getInfected() {
		return infected;
	}
	public void becomeInfected() {
		infected = true;
	}
	public void removeInfection() {
		infected = false;
	}
	
	public void printTileInfo() {
		System.out.println("TileNum - " + tileNum + "\n\tLeftSlot - " + getLeftSlot() + "\n\tTopSlot - " + getTopSlot() + "\n\tRightSlot - " + getRightSlot() + "\n\tBottomSlot - " + getBottomSlot());
	}
}
