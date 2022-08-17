package proceduralTesting;

import java.util.Arrays;
import java.util.Random;

public class MapAI {
//	Random ran;
	MapChecker checker;
	
	public MapAI() {
		checker = new MapChecker();
	}
	
	public Tile[][] genAlgMap(int height, int width){
		Tile[][] map = new Tile[height][width];
		Tile[][] map2 = new Tile[height][width];
		
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				map[i][j] = new Tile();
				map2[i][j] = new Tile();
			}
		}
		
		//what can I do with this? i want to make an AI, but what will it improve upon over time?
			//what to do with this??????????????
			//
		//FUCK!!!
			//ok, lets start small. I can fuck with path length and direction. Thats, about, it.
			//deal with direction? count every up and down, or left-right, and reward points based on that.
			//then split the map? randomly select tiles from each, or split the map at some part and fill like that
		
		long seed = /*1234*/0;
		long seed2 = /*4321*/0;
		long rngSeed = /*12345*/0;
		populateMapVineMethod(map, seed, checker);
		populateMapVineMethod(map2, seed2, checker);
		System.out.println(printMap(map));
		System.out.println(printMap(map2));
		System.out.println("Map 1 Value - " + getMapValue(map));
		System.out.println("Map 2 Value - " + getMapValue(map2));
		String genetics = assignString(map);
		String genetics2 = assignString(map2);
		System.out.println("Map 1 Genes - " + genetics);
		System.out.println("Map 2 Genes - " + genetics2);
		map = decodeGenetics(genetics, map.length, map[0].length);
		System.out.println("Map 1 - \n" + printMap(map));
		System.out.println("Map 2 - \n" + printMap(map2));
		Tile[][] newMap = mixAndMutate(map, map2, rngSeed);
		System.out.println("New Map Genes - " + assignString(newMap));
		System.out.println(printMap(newMap));
		System.out.println("New Map Value - " + getMapValue(newMap));
		
		return map;
	}
	
	public Tile[][] mixAndMutate(Tile[][] map1, Tile[][] map2, long rngSeed) {
		int value1 = getMapValue(map1);
		int value2 = getMapValue(map2);
		
		String gene1 = assignString(map1);
		String gene2 = assignString(map2);
		
		Tile[][] outputMap;
		System.out.println("Gene1 - " + gene1 + "\nGene2 - " + gene2);
		
		Random rng = new Random();
		int order = rng.nextInt(2);
		
		order = 1;
		
		double ratio;
		double dsplitPoint;
		int splitPoint;
		String sub1;
		String sub2;
		String newMap = "";
		if(value1 > value2) {
			//split with more on map1
			ratio = (double)value2 / (double)value1;
			//ratio wont work here like i have it
			dsplitPoint = (double)gene1.length()/2 * ratio;
			splitPoint = (int)dsplitPoint;
			System.out.println("Splitpoint - " + splitPoint);
		} else if (value2 > value1) {
			//split with more on map2
			ratio = (double)value1 / (double)value2;
			dsplitPoint = (double)gene1.length()/2 * ratio;
			splitPoint = (int)dsplitPoint;
		} else {
			//split evenly
			splitPoint = gene1.length() / 2;
			System.out.println(splitPoint);
		}
		if(order == 0) {
			sub2 = gene2.substring(splitPoint);
			sub1 = gene1.substring(0, (gene1.length() - 1) - splitPoint);
		} else {
			sub1 = gene1.substring(splitPoint);
			sub2 = gene2.substring(0, gene1.length() - splitPoint);
		}
		System.out.println("Sub1 - " + sub1 + "\nSub2 - " + sub2);
		newMap += sub1;
		newMap += sub2;
		
		outputMap = decodeGenetics(newMap, map1.length, map1[0].length);
		//now for the mutate part
		//go through the entire map, check for non connected tiles, and connect them.
		
		//new VineMethod will go here
		System.out.println(printMap(outputMap));
		infectionMutation(outputMap, rngSeed);
		//
//		for(int r = 0; r < 10; r++) {
		for(int i = 0; i < outputMap.length; i++) {
			for(int j = 0; j < outputMap[i].length; j++) {
				if(outputMap[i][j].getTopSlot() == true) {
					try {
						if(outputMap[i-1][j].getBottomSlot() == false) {
							outputMap[i-1][j].setBottomSlot(true);
							outputMap[i-1][j].setTile();
						}
					} catch (Exception e) {
//						System.out.println("Error on TOP Slot at " + i + ", " + j);
						outputMap[i][j].setTopSlot(false);
						outputMap[i][j].setTile();
					}
				}
				if(outputMap[i][j].getBottomSlot() == true) {
					try {
						if(outputMap[i+1][j].getTopSlot() == false) {
							outputMap[i+1][j].setTopSlot(true);
							outputMap[i+1][j].setTile();
						}
					} catch (Exception e) {
//						System.out.println("Error on BOTTOM Slot at " + i + ", " + j);
						outputMap[i][j].setBottomSlot(false);
						outputMap[i][j].setTile();
					}			
				}
				if(outputMap[i][j].getLeftSlot() == true) {
					try {
						if(outputMap[i][j-1].getRightSlot() == false) {
							outputMap[i][j-1].setRightSlot(true);
							outputMap[i][j-1].setTile();
						}
					} catch (Exception e) {
//						System.out.println("Error on LEFT Slot at " + i + ", " + j);
						outputMap[i][j].setLeftSlot(false);
						outputMap[i][j].setTile();
					}
				}
				if(outputMap[i][j].getRightSlot() == true) {
					try {
						if(outputMap[i][j+1].getLeftSlot() == false) {
							outputMap[i][j+1].setLeftSlot(true);
							outputMap[i][j+1].setTile();
						}
					} catch (Exception e) {
//						System.out.println("Error on RIGHT Slot at " + i + ", " + j);
						outputMap[i][j].setRightSlot(false);
						outputMap[i][j].setTile();
					}
				}
			}
		}
//		}
		
		
		
		
		
		return outputMap;
	}
	
	public void fixMapVineMethod(Tile[][] map, long seed, MapChecker checker) {
		//so I need to jump around, check the adjacent for infection, and if its there then combine.
			//but need to spread it to all connected tiles asap
		//wat do?
		//need to connect all connected pieces at once, one solid go. how do? MapChecker checkMap but keep the infection?
		
		
		
		
		Random rng = new Random();
		if(seed != 0) {
			rng = new Random(seed);
		}
		int col2 = 0;
		int row2 = 0;
		map[row2][col2].becomeInfected();
		while(checker.checkMapInfect2(map) == false) {
			//main difference is they are already connected, need to spread it to entire sections
			//i think this should work on partially broken maps, bottom is unneeded. Bottom will hopefully fix shattered maps
			for(int r = 0; r < 3; r++) {
				for(int i = 0; i < map.length; i++) {
					for(int j = 0; j < map[i].length; j++) {
						//read all adjacent
						if(map[i][j].infected2 == true) {
							try {
								if(map[i][j].getTopSlot() == true) {
									map[i-1][j].setBottomSlot(true);
									map[i-1][j].infected2 = true;
									map[i-1][j].setTile();
								}
							}catch(Exception e){
							}
							try {
								if(map[i][j].getBottomSlot() == true) {
									map[i+1][j].setTopSlot(true);
									map[i+1][j].infected2 = true;
									map[i+1][j].setTile();
								}
							}catch(Exception e){
							}
							try {
								if(map[i][j].getLeftSlot() == true) {
									map[i][j-1].setRightSlot(true);
									map[i][j-1].infected2 = true;
									map[i][j-1].setTile();
								}
							}catch(Exception e){
							}
							try {
								if(map[i][j].getRightSlot() == true) {
									map[i][j+1].setLeftSlot(true);
									map[i][j+1].infected2 = true;
									map[i][j+1].setTile();
								}
							}catch(Exception e){
							}
						}
					}
				}
			}
			
			
			
			int row = rng.nextInt(map.length);
			int col = rng.nextInt(map[row].length);
			boolean[] test = {false, false, false, false};
			if(checker.infectedAdjacent(map, row, col) != test) {
				boolean[] sides = checker.infectedAdjacent(map, row, col);
				int selectedTile = -1;
				int trueCount = 0;
				for(boolean bool: sides) {
					if(bool == false) {
						
					} else {
						trueCount += 1;
					}
				}
				if(trueCount == 1) {
					map[row][col].becomeInfected();
					if(sides[0] == true) {
						map[row][col].setTopSlot(true);
						map[row][col].setTile();
						map[row-1][col].setBottomSlot(true);
						map[row-1][col].setTile();
					}
					if(sides[1] == true) {
						map[row][col].setBottomSlot(true);
						map[row][col].setTile();
						map[row+1][col].setTopSlot(true);
						map[row+1][col].setTile();
					}
					if(sides[2] == true) {
						map[row][col].setLeftSlot(true);
						map[row][col].setTile();
						map[row][col-1].setRightSlot(true);
						map[row][col-1].setTile();
					}
					if(sides[3] == true) {
						map[row][col].setRightSlot(true);
						map[row][col].setTile();
						map[row][col+1].setLeftSlot(true);
						map[row][col+1].setTile();
					}
				} else if(trueCount >= 2) {
					map[row][col].becomeInfected();
					boolean cont = false;
					while(cont == false) {
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
		
	}
	
	public int getMapValue(Tile[][] map) {
		int value = 0;
		
		for(Tile[] row: map) {
			for(Tile tile: row) {
				if(true) {
					//0,1,2,3,6,7,8,9,10,11,13
					//bonus for 6,11,13 since no L/R tiles,
					//split it into 4, those who give 1, give 2, and bonus for none of the others
					//points deducted for having others?
						//+2 for up or down, -1 for each left/right
					if(tile.getTileNum() == 11 || tile.getTileNum() == 13) {
						value += 2;
					} else if(tile.getTileNum() == 1 || tile.getTileNum() == 3) {
						value += 3;
					} else if(tile.getTileNum() == 6) {
						value += 4;
					} else if(tile.getTileNum() == 7 || tile.getTileNum() == 8 || tile.getTileNum() == 9 || tile.getTileNum() == 10 || tile.getTileNum() == 0) {
						//i dont want to reward tile 0 all that much so im reducing the value of it
						value += 1;
					} else if (tile.getTileNum() == 12 || tile.getTileNum() == 14) {
						value -= 1;
					} else if (tile.getTileNum() == 5) {
						value -= 2;
					}
				}/* else {
					//0,1,2,3,4,7,8,9,10,12,14
					//bonus for 5
					if(tile.getTileNum() == 0 || tile.getTileNum() == 1 || tile.getTileNum() == 2 || tile.getTileNum() == 3 || tile.getTileNum() == 7 || tile.getTileNum() == 8 || tile.getTileNum() == 9 || tile.getTileNum() == 10) {
						value += 1;
					} else if(tile.getTileNum() == 6 || tile.getTileNum() == 11 || tile.getTileNum() == 13) {
						value += 5;
					}
				}*/
			}
		}
		
		return value;
	}
	
	public String assignString(Tile[][] map) {
		String genetics = "";
		
		for(Tile[] mapRow: map) {
			for(Tile grid: mapRow) {
				switch(grid.getTileNum()) {
				case 0:
					genetics += "a";
					break;
				case 1:
					genetics += "b";
					break;
				case 2:
					genetics += "c";
					break;
				case 3:
					genetics += "d";
					break;
				case 4:
					genetics += "e";
					break;
				case 5:
					genetics += "f";
					break;
				case 6:
					genetics += "g";
					break;
				case 7:
					genetics += "h";
					break;
				case 8:
					genetics += "i";
					break;
				case 9:
					genetics += "j";
					break;
				case 10:
					genetics += "k";
					break;
				case 11:
					genetics += "l";
					break;
				case 12:
					genetics += "m";
					break;
				case 13:
					genetics += "n";
					break;
				case 14:
					genetics += "o";
					break;
				}
			}
		}
		
		return genetics;
	}
	
	
	public Tile[][] decodeGenetics(String genetics, int height, int width){
		Tile[][] map = new Tile[height][width];
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				map[i][j] = new Tile();
			}
		}
		System.out.println("decodeGenetics - " + genetics + ", " + height + ", " + width);
		String[] code = genetics.split("");
		int num = 0;
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				switch(code[num]) {
				case "a":
					map[i][j].setTileNum(0);
					break;
				case "b":
					map[i][j].setTileNum(1);
					break;
				case "c":
					map[i][j].setTileNum(2);
					break;
				case "d":
					map[i][j].setTileNum(3);
					break;
				case "e":
					map[i][j].setTileNum(4);
					break;
				case "f":
					map[i][j].setTileNum(5);
					break;
				case "g":
					map[i][j].setTileNum(6);
					break;
				case "h":
					map[i][j].setTileNum(7);
					break;
				case "i":
					map[i][j].setTileNum(8);
					break;
				case "j":
					map[i][j].setTileNum(9);
					break;
				case "k":
					map[i][j].setTileNum(10);
					break;
				case "l":
					map[i][j].setTileNum(11);
					break;
				case "m":
					map[i][j].setTileNum(12);
					break;
				case "n":
					map[i][j].setTileNum(13);
					break;
				case "o":
					map[i][j].setTileNum(14);
					break;
				}
				
				num++;
			}
		}
		for(Tile[] row: map) {
			for(Tile tile: row) {
				tile.setSlot();
			}
		}
		return map;
	}
	
	
	
 	public Tile[][] createMapRNGMethod(int width, int height){
		//this method is pretty much useless until I can find a more effective way of doing, probably where I can collect the potential 
 			//adjacent tiles and choose between only vlaid options instead of both valid and invalid
 		Tile[][] map = new Tile[height][width];
		
		return map;
	}
	
	public void populateMapVineMethod(Tile[][] map, long seed, MapChecker checker) {
		Random rng = new Random();
		if(seed != 0) {
			rng = new Random(seed);
		}
		int col2 = 0;
		int row2 = 0;
		map[row2][col2].becomeInfected();
		while(checker.checkMapInfect2(map) == false) {
			int row = rng.nextInt(map.length);
			int col = rng.nextInt(map[row].length);
			boolean[] test = {false, false, false, false};
			if(checker.infectedAdjacent(map, row, col) != test) {
				boolean[] sides = checker.infectedAdjacent(map, row, col);
				int selectedTile = -1;
				int trueCount = 0;
				for(boolean bool: sides) {
					if(bool == false) {
						
					} else {
						trueCount += 1;
					}
				}
				if(trueCount == 1) {
					map[row][col].becomeInfected();
					if(sides[0] == true) {
						map[row][col].setTopSlot(true);
						map[row][col].setTile();
						map[row-1][col].setBottomSlot(true);
						map[row-1][col].setTile();
					}
					if(sides[1] == true) {
						map[row][col].setBottomSlot(true);
						map[row][col].setTile();
						map[row+1][col].setTopSlot(true);
						map[row+1][col].setTile();
					}
					if(sides[2] == true) {
						map[row][col].setLeftSlot(true);
						map[row][col].setTile();
						map[row][col-1].setRightSlot(true);
						map[row][col-1].setTile();
					}
					if(sides[3] == true) {
						map[row][col].setRightSlot(true);
						map[row][col].setTile();
						map[row][col+1].setLeftSlot(true);
						map[row][col+1].setTile();
					}
				} else if(trueCount >= 2) {
					map[row][col].becomeInfected();
					boolean cont = false;
					while(cont == false) {
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
	}
	
	public String printMap(Tile[][] map) {
		System.out.println("Printing Map");
		String[] miniTiles = {"---"," --","-- "," | ", "   "};
		String toPrint = "";
		String[][][] miniTilesMap = new String[map.length][map[0].length][3];
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
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
		for(int i = 0; i < map.length; i++) {
			for(int r = 0; r < 3; r++) {
				for(int j = 0; j < map[i].length; j++) {
					toPrint += miniTilesMap[i][j][r];
				}
				toPrint += "\n";
			}
		}
		return toPrint;
	}
	
	
	
	public void infectionMutation(Tile[][] map, long seed) {
		int[][] adjTiles = new int[map.length * map[0].length][2];
		Random rng = new Random();
		if(seed != 0) {
			rng = new Random(seed);
		}
		
		MapChecker checker = new MapChecker();
		
		//need to spread infection using a method, but be able to spread it to the whole section first.
		//MapChecker.checkMapInfect2(map)
			//could use an ineffective variant of that method, just spread it to the section but not close out after its done.
				//Then check with a noInfect method to see if it is complete. WHile it isnt, get adjacent tiles and choose between those.
				//mutate that tile to fit, then loop again. Repeat until done.
			//OMG, Should be able to copy all but the last loop that marks it as false. Use that loop later for final check. 
				//wait, i dont think i need this to be recursive, the checkMapInfect2 should work it out fine without that
				//no need for recursion in this method
				//so after the first loop, need to check if valid. If it is, end there.
					//If it is not, gather all tiles adjacent to infected tiles BUT ARE NOT INFECTED into adjTiles.
						//then put them into a smaller list that they fit perfectly into, then use RNG to determine one to mutate.
						//after mutation, loop again and let any new tiles get infected. The rest plays out until it mutates into a valid map23
		boolean finished = false;
		while(/*finished*/checker.checkMapv2(map) == false) {
			boolean[][] infectedMap = new boolean[map.length][map[0].length];
			//wipes map to ensure results
			for(int i = 0; i < map.length; i++) {
				for(int j = 0; j < map[i].length; j++) {
					infectedMap[i][j] = false;
					map[i][j].infected2 = false;
				}
			}
			
			map[0][0].infected2 = true;
			
			for(int r = 0; r < 5; r++) {
			for(int i = 0; i < map.length; i++) {
				for(int j = 0; j < map[i].length; j++) {
					//check above
					try {
						if(map[i][j].getTopSlot() == true && map[i-1][j].getBottomSlot() == true && (map[i][j].infected2 == true || map[i-1][j].infected2 == true)) {
							if(map[i-1][j].infected2 == true) {
								map[i][j].infected2 = true;
							}else{
								map[i-1][j].infected2 = true;
							}
						}
					} catch (IndexOutOfBoundsException e) {
					}
					//check below
					try {
						if(map[i][j].getBottomSlot() == true && map[i+1][j].getTopSlot() == true && (map[i][j].infected2 == true || map[i+1][j].infected2 == true)) {
							if(map[i+1][j].infected2 == true) {
								map[i][j].infected2 = true;
							}else{
								map[i+1][j].infected2 = true;
							}
						}
					} catch (IndexOutOfBoundsException e) {
					}
					//check left
					try {
						if(map[i][j].getLeftSlot() == true && map[i][j-1].getRightSlot() == true && (map[i][j].infected2 == true || map[i][j-1].infected2 == true)) {
							if(map[i][j-1].infected2 == true) {
								map[i][j].infected2 = true;
							}else{
								map[i][j-1].infected2 = true;
							}
						}
					} catch (IndexOutOfBoundsException e) {
					}
	
					//check right
					try {
						if(map[i][j].getRightSlot() == true && map[i][j+1].getLeftSlot() == true && (map[i][j].infected2 == true || map[i][j+1].infected2 == true)) {
							if(map[i][j+1].infected2 == true) {
								map[i][j].infected2 = true;
							}else{
								map[i][j+1].infected2 = true;
							}
						}
					} catch (IndexOutOfBoundsException e) {
						//System.out.println("Error on check right");
					}
				}
			}
			}
				
			for(int i = 0; i < map.length; i++) {
				for(int j = 0; j < map[i].length; j++) {
					infectedMap[i][j] = map[i][j].infected2;
				}
			}
			
			boolean rerun = false;
			for(int i = 0; i < infectedMap.length; i++) {
				for(int j = 0; j < infectedMap[i].length; j++) {
					if (infectedMap[i][j] == false) {
						rerun = true;
					}
				}
			}
//			System.out.println("Map Checker - " + checker.checkMapv2(map));
//			System.out.println("Rerun - " + rerun);
			finished = !rerun;
			
			if (checker.checkMapv2(map)/*finished*/ == false) {
//				System.out.println("Mutate Map Now");
				int adjCount = 0;
				//check every tile, for all directly adjacent to an infected one BUT NOT INFECTED ITSELF, put coordinates into adjTiles.
				//once thats done, put them into a smaller array, then choose a random one to mutate.
//				int tileCount = 0;

				for(int i = 0; i < map.length; i++) {
					for (int j = 0; j < map[i].length; j++) {
						//put coords into array, count them
						boolean[] test = {false, false, false, false};
						if(!Arrays.equals(checker.infectedAdjacent2(map, i, j), test)) {
							
							adjTiles[adjCount][0] = i;
							adjTiles[adjCount][1] = j;
//							tileCount += 1;
							adjCount += 1;
						}
					}
				}
				

				int[][] adjacent = new int[adjCount][2];
//				for(int[] x: adjTiles) {
//					System.out.println("Possible Infect - " + x[0] + ", " + x[1]);
//					adjacent[adjCount-1] = x;
//					adjCount -= 1;
//				}
				for(int i = 0; i < adjacent.length; i++) {
					adjacent[i][0] = adjTiles[i][0];
					adjacent[i][1] = adjTiles[i][1];
//					System.out.println("Possible Infect - " + adjacent[i][0] + ", " + adjacent[i][1]);
				}
				
				//now select and mutate the tile
				int mutateTile = rng.nextInt(adjacent.length);
				int mutRow = adjacent[mutateTile][0];
				int mutCol = adjacent[mutateTile][1];
//				System.out.println("Mutated Tile - " + mutRow + ", " + mutCol);
				boolean[] mutTile = checker.infectedAdjacent(map, mutRow, mutCol);
				//mutate tile and adjacents
				//looks like {top, bottom, left, right}

				int chosenSide = rng.nextInt(4);
				boolean mutated = false;
//				System.out.println(printMap(map));
				if(mutTile[0] == true && mutated == false) {
//					System.out.println("Adjusting Top Slot");
					map[mutRow][mutCol].setTopSlot(true);
					map[mutRow][mutCol].setTile();
					mutated = true;
//					System.out.println("Top Mutated for " + mutRow + ", " + mutCol);
					try {
						if(map[mutRow-1][mutCol].getBottomSlot() == false) {
//							System.out.println("Mutating bottom slot");
							map[mutRow-1][mutCol].setBottomSlot(true);
//							System.out.println("Test");
							map[mutRow-1][mutCol].setTile();
//							System.out.println("Test 2");
//							System.out.println("Tile num - " + map[mutCol-1][mutCol].getTileNum());
						}
					} catch (Exception e) {
//						System.out.println("Error on TOP Slot at " + i + ", " + j);
						System.out.println(e);
					}
				}
				if(mutTile[1] == true && mutated == false) {
//					System.out.println("Adjusting Bottom Slot");
					map[mutRow][mutCol].setBottomSlot(true);
					map[mutRow][mutCol].setTile();
					mutated = true;
//					System.out.println("Bottom Mutated for " + mutRow + ", " + mutCol);
					try {
						if(map[mutRow+1][mutCol].getTopSlot() == false) {
							map[mutRow+1][mutCol].setTopSlot(true);
							map[mutRow+1][mutCol].setTile();
						}
					} catch (Exception e) {
//						System.out.println("Error on BOTTOM Slot at " + i + ", " + j);
						System.out.println(e);
					}			
				}
				if(mutTile[2] == true && mutated == false) {
//					System.out.println("Adjusting Left Slot");
					map[mutRow][mutCol].setLeftSlot(true);
					map[mutRow][mutCol].setTile();
					mutated = true;
//					System.out.println("Left Mutated for "  + mutRow + ", " + mutCol);
					try {
						if(map[mutRow][mutCol-1].getRightSlot() == false) {
							map[mutRow][mutCol-1].setRightSlot(true);
							map[mutRow][mutCol-1].setTile();
						}
					} catch (Exception e) {
//						System.out.println("Error on LEFT Slot at " + i + ", " + j);
						System.out.println(e);
					}
				}
				if(mutTile[3] == true && mutated == false) {
//					System.out.println("Adjusting Right Slot");
					map[mutRow][mutCol].setRightSlot(true);
					map[mutRow][mutCol].setTile();
					mutated = true;
//					System.out.println("Right Mutated for " + mutRow + ", " + mutCol);
					try {
						if(map[mutRow][mutCol+1].getLeftSlot() == false) {
							map[mutRow][mutCol+1].setLeftSlot(true);
							map[mutRow][mutCol+1].setTile();
						}
					} catch (Exception e) {
//						System.out.println("Error on RIGHT Slot at " + i + ", " + j);
						System.out.println(e);
					}
				}
			}
			
		}	
	}
	
	
}
