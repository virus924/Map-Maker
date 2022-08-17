package proceduralTesting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
//import java.util.Scanner;



public class WorldGenerator {
	public static void main(String[] args) throws IOException {
		Tile[][] map = new Tile[2][2];//16x16 will be the final size
		Random rng = new Random();
		
		File file = new File("C:/CodingStuffs/MapGen.txt");
		FileWriter writer = new FileWriter(file, true);
		
//		ObjectOutputStream oos = new ObjectOutputStream();
//		ObjectInputStream ois = new ObjectInputStream();
		
		//HashMap<String, Boolean> tested = new HashMap<String, Boolean>();
		
		MapChecker checker = new MapChecker();
		MapAI ai = new MapAI();
		
//		populateMap(map, rng);
//		for(int i = 0; i < map.length; i++) {
//			for(int j = 0; j < map[i].length; j++) {
//				map[i][j].setTileNum(0);
//			}
//		}
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				map[i][j] = new Tile();
			}
		}
		
		if(false) {
//		System.out.println("Test Map Value - " + checker.checkMapv2(map));
//		System.out.println("Starting Program");
//		int test = 0;
//		while(/*checker.checkMapv3(map)*/checker.checkMapNoInfect(map) == false) {
//			//populateMap(map, rng);
//			populateMapVineMethod(map, rng, checker);
//			System.out.println(test);
//			test++;
//		}
//		populateMapVineMethod(map, rng, checker);
//		long rngSeed;
		int rngSeed = rng.nextInt();
		long seed = (long) rngSeed;
		if(seed == 0) {
			seed = rng.nextLong();
		}
		
		System.out.println("Seed - " + seed + "\tSize - " + map.length + "x" + map[0].length);
		populateMapVineMethod(map, seed, checker);
//		System.out.println(getMapInfo(map));
		String toPrint = printMap(map);
		System.out.println(toPrint);
		writer.write("Seed - \t" + seed + "\tSize - " + map.length + "x" + map[0].length + "\n" + toPrint);
		
		/*
		int[] set = new int[map.length * map[0].length];
		String strSet = "";
		boolean checkedMap = false;
		while(checkedMap == false) {
			set = populateMapv2(map, rng);
			strSet = setToString(set);
			if(tested.containsKey(strSet) == true) {
				checkedMap = (boolean) tested.get(strSet);
			} else {
				checkedMap = checker.checkMapv3(map);
				tested.put(strSet, checkedMap);
			}
		}
		tested.put(strSet, checkedMap);
		//System.out.println(set.toString());
		int tempNum = 0;
		for(String name: tested.keySet()) {
			//String key = name;
			String value = tested.get(name).toString();
			System.out.println("#" + tempNum + " - " + name + " - " + value);
			tempNum++;
		}
		System.out.println(getMapInfo(map));
		*/
		} else {
			//Tile[][] map = new Tile[3][3];
			/*
			*	ai.populateMapVineMethod(map, 1234, checker);
			*	System.out.println(ai.printMap(map));
			*	System.out.println(ai.assignString(map));
			*/
			ai.genAlgMap(10, 10);
		}
		writer.close();
	}
	
	public static String setToString(int[] set) {
		String returnSet = "";
		for(int i = 0; i < set.length; i++) {
			if(i == set.length - 1) {
				returnSet += set[i];
			} else {
				returnSet += set[i] + ", ";
			}
		}
		return returnSet;
	}
	
	public static void populateMap(Tile[][] map, Random rng) {
//		int tempNum = 0;
//		int[] tileSet = {8,10,13,9,0,1,3,2,4};
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				//System.out.println("i - " + i + "\tj - " + j);
				map[i][j] = new Tile();
				//map[i][j].printTileInfo();
				map[i][j].setTileNum(rng);
				//map[i][j].setTileNum(0);
//				map[i][j].setTileNum(tileSet[tempNum]);
				//map[i][j].printTileInfo();
//				tempNum++;
			}
		}	
	}
	
	public static int[] populateMapv2(Tile[][] map, Random rng) {
		int[] set = new int[map.length * map[0].length];
		int tileCount = 0;
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				map[i][j] = new Tile();
				map[i][j].setTileNum(rng);
				set[tileCount] = map[i][j].getTileNum();
				tileCount++;
			}
		}	
		
		return set;
	}
	
	public static void populateMapVineMethod(Tile[][] map, /*Random rng*/long seed, MapChecker checker) {
		
		//so now I need to find an algorithm so I can use a seed for generation
		//seed will determine the order that the blocks are chosen, giving the same map each time
		//i can use either 3x+1 and /2 if even, OR i can use the other one x(n+1) = rx(n)(1-x(n))
			//the 2nd one has to be past the point where it goes random
		Random rng = new Random();
		if(seed != 0) {
			rng = new Random(seed);
			//need to make it so that it doesnt require a seed, proper random, and it stores the seed for retrieval
		}
//		System.out.println("Seed - " + rng.);
//		else {
//			Random rng = new Random();
//		}

//		int seed = 10;
//		for(int i = 0; i < 15; i++) {
//			int tile = seed;
//			
//		}
		
		
		
		
		int col2 = 0;
		int row2 = 0;
		map[row2][col2].becomeInfected();
		//System.out.println("Map Size - " + map.length + "x" + map[row2].length);
		
		//choose random tile
		while(checker.checkMapInfect2(map) == false) {
//		for(int i = 0; i < 5; i++) {
			//System.out.println(getMapInfo(map));
			//System.out.println("STARTING LOOP " + (i+1) + " ------------------");
			//System.out.println(checker.checkMapv3(map));
			//i can improve RNG efficiency by eliminating "solved" tiles so it wont check again, need a list for that
				//list of 0-X, then a switch to determine coordinates?
			//int row2 = rng.nextInt(map.length - 1);
			
			//System.out.println("Row - " + row);
			//System.out.println("Map Length - " + map.length);
			int row = rng.nextInt(map.length);
			//System.out.println("Row after - " + row);
			int col = rng.nextInt(map[row].length);
			
//			switch(i) {
//			case 0:
//				row = 0;
//				col = 1;
//				break;
//			case 1:
//				row = 1;
//				col = 0;
//				break;
//			case 2:
//				row = 1;
//				col = 1;
//				break;
//			default:
//				row = 0;
//				col = 0;
//				break;
//			}
			//System.out.println("Row and Col - " + row + ", " + col);
			
			boolean[] test = {false, false, false, false};
			//boolean[] bRay = checker.infectedAdjacent(map, row, col);
			//i think this is gonna be picky about it, might have to change off of boolean
			if(/*allFalseArray(bRay) != false*/ checker.infectedAdjacent(map, row, col) != test) {
				//System.out.println("133");
				//ok, so I need to have it... tiles, change them, something
				//need to check which sides are available, choose one
				boolean[] sides = checker.infectedAdjacent(map, row, col);
				//infectedAdjacent isnt registering
				int selectedTile = -1;
				int trueCount = 0;
				//int falseCount = 0;
				for(boolean bool: sides) {
					if(bool == false) {
						//falseCount += 1;
					} else {
						trueCount += 1;
					}
				}
				//System.out.println("TrueCount - " + trueCount);
						//left, right, up, down
				//up down left right, this is the actual version
				//first the easy one
				if(trueCount == 1) {
					map[row][col].becomeInfected();
					//System.out.println("Run trueCount == 1");
					if(sides[0] == true) {
						//System.out.println("TC1 up");
						map[row][col].setTopSlot(true);
						map[row][col].setTile();
						map[row-1][col].setBottomSlot(true);
						map[row-1][col].setTile();
					}
					if(sides[1] == true) {
						//System.out.println("TC1 down");
						map[row][col].setBottomSlot(true);
						map[row][col].setTile();
						map[row+1][col].setTopSlot(true);
						map[row+1][col].setTile();
					}
					if(sides[2] == true) {
						//System.out.println("TC1 left");
						map[row][col].setLeftSlot(true);
						map[row][col].setTile();
						map[row][col-1].setRightSlot(true);
						map[row][col-1].setTile();
					}
					if(sides[3] == true) {
						//System.out.println("TC1 right");
						map[row][col].setRightSlot(true);
						map[row][col].setTile();
						map[row][col+1].setLeftSlot(true);
						map[row][col+1].setTile();
					}
				} else if(trueCount >= 2) {
					//System.out.println("Run trueCount >= 2");
					map[row][col].becomeInfected();
					boolean cont = false;
					while(cont == false) {
						//System.out.println("186");
						selectedTile = rng.nextInt(4);
						if(sides[selectedTile] == true) {
							cont = true;
						}
					}
					if(selectedTile == 0) {
						map[row][col].setTopSlot(true);
						map[row][col].setTile();
						map[row-1][col].setBottomSlot(true);
						map[row-1][col].setTile();
					}
					if(selectedTile == 1) {
						map[row][col].setBottomSlot(true);
						map[row][col].setTile();
						map[row+1][col].setTopSlot(true);
						map[row+1][col].setTile();
					}
					if(selectedTile == 2) {
						map[row][col].setLeftSlot(true);
						map[row][col].setTile();
						map[row][col-1].setRightSlot(true);
						map[row][col-1].setTile();
					}
					if(selectedTile == 3) {
						map[row][col].setRightSlot(true);
						map[row][col].setTile();
						map[row][col+1].setLeftSlot(true);
						map[row][col+1].setTile();
					}
				}
			}
		}
//		}
		//getMapInfo(map);
		//wipeInfection(map);
	}
	
	public static void updateMap(Tile[][] map) {
		//this will be opposite of tile.setSlot(), take the slots and determine the tileNum
	}
	
	public static void wipeInfection(Tile[][] map) {
		for(Tile[] layer: map) {
			for(Tile tile: layer) {
				tile.removeInfection();
			}
		}
	}
	
	public static boolean allFalseArray(boolean[] bRay) {
		for(boolean bool: bRay) {
			if (bool == true) {
				return false;
			}
		}
		return true;
	}
	
	public static String getMapInfo(Tile[][] map) {
		String info = "";
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				info += i + ", " + j + " - tileNum = " + map[i][j].getTileNum() + "\n";
			}
		}
		
		return info;
	}
	
	public static String printMap(Tile[][] map) {
		String[] tiles = {" | \n---\n | ", " | \n-- \n | ", "   \n---\n | ", " | \n --\n | ", " | \n---\n   ", "   \n---\n   ", " | \n | \n | ",
				"   \n-- \n | ", "   \n --\n | ", " | \n --\n   ", " | \n-- \n   ", " | \n   \n   ", "   \n  -\n   ", "   \n   \n | ", "   \n  -\n   "};
		String[] miniTiles = {"---"," --","-- "," | ", "   "};
		String toPrint = "";
		String[][][] miniTilesMap = new String[map.length][map[0].length][3];
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				//miniTilesMap[i][j] = map[i][j].getTileNum();
				switch(map[i][j].getTileNum()) {
				case 0:
					miniTilesMap[i][j][0] = miniTiles[3];
					miniTilesMap[i][j][1] = miniTiles[0];
					miniTilesMap[i][j][2] = miniTiles[3];
					break;
				case 1:
					miniTilesMap[i][j][0] = miniTiles[3];
					miniTilesMap[i][j][1] = miniTiles[2];
					miniTilesMap[i][j][2] = miniTiles[3];
					break;
				case 2:
					miniTilesMap[i][j][0] = miniTiles[4];
					miniTilesMap[i][j][1] = miniTiles[0];
					miniTilesMap[i][j][2] = miniTiles[3];
					break;
				case 3:
					miniTilesMap[i][j][0] = miniTiles[3];
					miniTilesMap[i][j][1] = miniTiles[1];
					miniTilesMap[i][j][2] = miniTiles[3];
					break;
				case 4:
					miniTilesMap[i][j][0] = miniTiles[3];
					miniTilesMap[i][j][1] = miniTiles[0];
					miniTilesMap[i][j][2] = miniTiles[4];
					break;
				case 5:
					miniTilesMap[i][j][0] = miniTiles[4];
					miniTilesMap[i][j][1] = miniTiles[0];
					miniTilesMap[i][j][2] = miniTiles[4];
					break;
				case 6:
					miniTilesMap[i][j][0] = miniTiles[3];
					miniTilesMap[i][j][1] = miniTiles[3];
					miniTilesMap[i][j][2] = miniTiles[3];
					break;
				case 7:
					miniTilesMap[i][j][0] = miniTiles[4];
					miniTilesMap[i][j][1] = miniTiles[2];
					miniTilesMap[i][j][2] = miniTiles[3];
					break;
				case 8:
					miniTilesMap[i][j][0] = miniTiles[4];
					miniTilesMap[i][j][1] = miniTiles[1];
					miniTilesMap[i][j][2] = miniTiles[3];
					break;
				case 9:
					miniTilesMap[i][j][0] = miniTiles[3];
					miniTilesMap[i][j][1] = miniTiles[1];
					miniTilesMap[i][j][2] = miniTiles[4];
					break;
				case 10:
					miniTilesMap[i][j][0] = miniTiles[3];
					miniTilesMap[i][j][1] = miniTiles[2];
					miniTilesMap[i][j][2] = miniTiles[4];
					break;
				case 11:
					miniTilesMap[i][j][0] = miniTiles[3];
					miniTilesMap[i][j][1] = miniTiles[4];
					miniTilesMap[i][j][2] = miniTiles[4];
					break;
				case 12:
					miniTilesMap[i][j][0] = miniTiles[4];
					miniTilesMap[i][j][1] = miniTiles[1];
					miniTilesMap[i][j][2] = miniTiles[4];
					break;
				case 13:
					miniTilesMap[i][j][0] = miniTiles[4];
					miniTilesMap[i][j][1] = miniTiles[4];
					miniTilesMap[i][j][2] = miniTiles[3];
					break;
				case 14:
					miniTilesMap[i][j][0] = miniTiles[4];
					miniTilesMap[i][j][1] = miniTiles[2];
					miniTilesMap[i][j][2] = miniTiles[4];
					break;
				}
			}
		}
		
//		for(int i = 0; i < map.length; i++) {
//			for(int j = 0; j < map[i].length; j++) {
//				toPrint += tiles[map[i][j].getTileNum()];
//			}
//		}
//		System.out.println(toPrint);
		
		for(int i = 0; i < map.length; i++) {
			for(int r = 0; r < 3; r++) {
				for(int j = 0; j < map[i].length; j++) {
					//need to get the lines, go slower. 3D array, based on the lines?
	//				System.out.print();
					//print out miniTiles
					toPrint += miniTilesMap[i][j][r];
					
				}
				toPrint += "\n";
			}
		}
		return toPrint;
	}
}
