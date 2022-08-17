package proceduralTesting;

//import com.sun.tools.javac.util.Dependencies.GraphDependencies.Node;
//import com.sun.org.apache.bcel.internal.classfile.Node;
//import com.sun.org.apache.xalan.internal.xsltc.runtime.Node;

public class MapChecker {
	//this should be able to read the map and determine if it is plausible.
	//it should make sure each tile is reachable, and if even one is not the seed is considered invalid.
	//i can do a depth first AI, turning the map into a node tree?
	//extended nodes are based on tile type (the 4 pronged tile can have up to 4 child nodes), but it doesnt read tiles already used to prevent loops
	
	//theres a difference between tiles and tile types. Tiles are the locations on the map, tile types are the 1-15
	
	private boolean[][] checkedMap;
	
	//Dictionary
		//i can use a dictionary to store if already used seeds and their boolean value, then check new seeds against it. CACHING
		//i can also use object output stream to keep the same MapChecker for a speed increase, instead of having to recheck it all
	
	private Tile neighbor;
		//this extra tile will just be to easily check if that neighbor is connected, will switch a lot
	
	public MapChecker() {
		
	}
	
	public boolean checkNodeTree(Tile[][] map) {
		//need a top node, current Node, and previous node
			//topnode will allow me to restart at the start. current will be, well, current. previous will allow me to backtrack
		//Node root = ;
		int row = 0;
		int col = 0;
		checkedMap = new boolean[map.length][map[0].length];
		for(int i = 0; i < checkedMap.length; i++) {
			for(int j = 0; j < checkedMap[i].length; j++) {
				checkedMap[i][j] = false;
			}
		}
		
		boolean[][] tempMap = checkedMap;
		
		Node topNode = new Node(map[row][col]);
		Node currentNode = topNode;
		Node previousNode = topNode;
		
		//need to create node tree and then trace it back
		//check top, right, bottom, then left
		
		
		
		if(checkedMap[row][col] == true) {
			//stop there,
			//this wont allow me to go back down an already used path start, fuck
			//could I use a 2nd boolean map to make sure it doesnt loop back at all while this one just checks if it has been visited?
		}
		
		
		
		return false;
	}
	
	public boolean checkMap(Tile[][] map) {
		checkedMap = new boolean[map.length][map[0].length];
		
		for(int i = 0; i < checkedMap.length; i++) {
			for(int j = 0; j < checkedMap[i].length; j++) {
				checkedMap[i][j] = false;
			}
		}
		
		
		//instead of a nested for loop, need to start in a corner and do a depth first and set the bool map to true as I go.
			//hit one thats true and connected to only trues, reset it. or maybe just one thats true? cache the path?
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				
				
				boolean top = false;
				boolean bottom = false;
				boolean left = false;
				boolean right = false;
				
				if(i == 0) {
					top = true;
				} else if(i == (map.length - 1)){
					bottom = true;
				}
				if(j == 0) {
					left = true;
				} else if(j == (map[i].length - 1)) {
					right = true;
				}
				
				//need to take current tile, scan all adjacent tiles, and tag each with true if reachable
				//go on [i][j-1], [i][j+1], [i-1][j], and [i+1][j]
				
				//all 4 false, runs through everything 		x1
				//only one is true, runs 3 of 4				x4
				//two are false, only runs 2 of 4			x4
				
				
				//should probably add a try catch to snag index out of bounds, just in case
				//this will allow islands to remain
				if(top == false) {
					neighbor = map[i+1][j];
					if(neighbor.getBottomSlot() == true && map[i][j].getTopSlot() == true) {
						checkedMap[i+1][j] = true;
					}
				}
				
				
				//basic run
				
			}
		}
		
		//if any tile at the end isnt hit, seed wont work and return false. if everyone is true, seed works, return true and write seed name
		
		for(int i = 0; i < checkedMap.length; i++) {
			for(int j = 0; j < checkedMap[i].length; j++) {
				if (checkedMap[i][j] == false) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public boolean checkMapv2(Tile[][] map) {
		boolean[][] infectedMap = new boolean[map.length][map[0].length];
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				infectedMap[i][j] = false;
			}
		}
		
//		if(top == false) {
//			neighbor = map[i+1][j];
//			if(neighbor.getBottomSlot() == true && map[i][j].getTopSlot() == true) {
//				checkedMap[i+1][j] = true;
//			}
//		}
		map[0][0].becomeInfected();
		
		
		for(int h = 0; h < 5; h++) {
			for(int i = 0; i < map.length; i++) {
				for(int j = 0; j < map[i].length; j++) {
					//System.out.println("v2 I = " + i + "\tv2 J = " + j);
					if(i != 0) {
						//check above
						//[i+1][j]
						try {
							if(map[i][j].getTopSlot() == true && map[i-1][j].getBottomSlot() == true && (map[i][j].getInfected() == true || map[i-1][j].getInfected() == true)) {
								if(map[i-1][j].getInfected() == true) {
									map[i][j].becomeInfected();
									//System.out.println((i-1) + ", " + j + "\tinfected " + i + ", " + j + "\ti != 0");
								}else{
									map[i-1][j].becomeInfected();
									//System.out.println(i + ", " + j + "\tinfected " + (i-1) + ", " + j + "\ti != 0");
								}
								//System.out.println(i + ", " + j + "\tinfected " + (i-1) + ", " + j + "\ti != 0");
							}
						} catch (IndexOutOfBoundsException e) {
							//System.out.println("Error on check above");
						}
					}
					if(i != map.length-1) {
						//check below
						try {
							if(map[i][j].getBottomSlot() == true && map[i+1][j].getTopSlot() == true && (map[i][j].getInfected() == true || map[i+1][j].getInfected() == true)) {
								if(map[i+1][j].getInfected() == true) {
									map[i][j].becomeInfected();
									//System.out.println((i+1) + ", " + j + "\tinfected " + i + ", " + j + "\ti != map.length - 1");
								}else{
									map[i+1][j].becomeInfected();
									//System.out.println(i + ", " + j + "\tinfected " + (i+1) + ", " + j + "\ti != map.length - 1");
								}
								//System.out.println(i + ", " + j + "\tinfected " + (i+1) + ", " + j + "\ti != map.length - 1");
							}
						} catch (IndexOutOfBoundsException e) {
							//System.out.println("Error on check below");
						}
					}
					if(/*j != map[i].length-1*/j != 0) {
						//check left
						try {
							if(map[i][j].getLeftSlot() == true && map[i][j-1].getRightSlot() == true && (map[i][j].getInfected() == true || map[i][j-1].getInfected() == true)) {
								if(map[i][j-1].getInfected() == true) {
									map[i][j].becomeInfected();
									//System.out.println(i + ", " + (j-1) + "\tinfected " + i + ", " + j + "\tj != map[i].length - 1");
								}else{
									map[i][j-1].becomeInfected();
									//System.out.println(i + ", " + j + "\tinfected " + i + ", " + (j-1) + "\tj != map[i].length - 1");
								}
								//System.out.println(i + ", " + j + "\tinfected " + i + ", " + (j-1) + "\tj != map[i].length - 1");
							}
						} catch (IndexOutOfBoundsException e) {
							//this will nullify stuffs
							//System.out.println("Error on check left");
						}
					}
					if(/*j != 0*/j != map[i].length-1) {
						//check right
						try {
							if(map[i][j].getRightSlot() == true && map[i+1][j].getLeftSlot() == true && (map[i][j].getInfected() == true || map[i][j+1].getInfected() == true)) {
								if(map[i][j+1].getInfected() == true) {
									map[i][j].becomeInfected();
									//System.out.println(i + ", " + (j+1) + "\tinfected " + i + ", " + j + "\tj != 0");
								}else{
									map[i][j+1].becomeInfected();
									//System.out.println(i + ", " + j + "\tinfected " + i + ", " + (j+1) + "\tj != 0");
								}
								//System.out.println(i + ", " + j + "\tinfected " + i + ", " + (j+1) + "\tj != 0");
							}
						} catch (IndexOutOfBoundsException e) {
							//System.out.println("Error on check right");
						}
					}
				}
			}
		}
			
			
			
//			for(int i = map.length - 1; i >= 0; i--) {
//				for(int j = map[i].length - 1; j >= 0; j--) {
//					//System.out.println("v2 I = " + i + "\tv2 J = " + j);
//					if(i != 0) {
//						//check above
//						//[i+1][j]
//						try {
//							if(map[i][j].getTopSlot() == true && map[i-1][j].getBottomSlot() == true && (map[i][j].getInfected() == true || map[i-1][j].getInfected() == true)) {
//								if(map[i-1][j].getInfected() == true) {
//									map[i][j].becomeInfected();
//									System.out.println((i-1) + ", " + j + "\tinfected " + i + ", " + j + "\ti != 0");
//								}else{
//									map[i-1][j].becomeInfected();
//									System.out.println(i + ", " + j + "\tinfected " + (i-1) + ", " + j + "\ti != 0");
//								}
//								//System.out.println(i + ", " + j + "\tinfected " + (i-1) + ", " + j + "\ti != 0");
//							}
//						} catch (IndexOutOfBoundsException e) {
//							
//						}
//					}
//					if(i != map.length-1) {
//						//check below
//						try {
//							if(map[i][j].getBottomSlot() == true && map[i+1][j].getTopSlot() == true && (map[i][j].getInfected() == true || map[i+1][j].getInfected() == true)) {
//								if(map[i+1][j].getInfected() == true) {
//									map[i][j].becomeInfected();
//									System.out.println((i+1) + ", " + j + "\tinfected " + i + ", " + j + "\ti != map.length - 1");
//								}else{
//									map[i+1][j].becomeInfected();
//									System.out.println(i + ", " + j + "\tinfected " + (i+1) + ", " + j + "\ti != map.length - 1");
//								}
//								//System.out.println(i + ", " + j + "\tinfected " + (i+1) + ", " + j + "\ti != map.length - 1");
//							}
//						} catch (IndexOutOfBoundsException e) {
//							
//						}
//					}
//					if(j != map[i].length-1) {
//						//check left
//						try {
//							if(map[i][j].getLeftSlot() == true && map[i][j-1].getRightSlot() == true && (map[i][j].getInfected() == true || map[i][j-1].getInfected() == true)) {
//								if(map[i][j-1].getInfected() == true) {
//									map[i][j].becomeInfected();
//									System.out.println(i + ", " + (j-1) + "\tinfected " + i + ", " + j + "\tj != map[i].length - 1");
//								}else{
//									map[i][j-1].becomeInfected();
//									System.out.println(i + ", " + j + "\tinfected " + i + ", " + (j-1) + "\tj != map[i].length - 1");
//								}
//								//System.out.println(i + ", " + j + "\tinfected " + i + ", " + (j-1) + "\tj != map[i].length - 1");
//							}
//						} catch (IndexOutOfBoundsException e) {
//							//this will nullify stuffs
//						}
//					}
//					if(j != 0) {
//						//check right
//						try {
//							if(map[i][j].getRightSlot() == true && map[i+1][j].getLeftSlot() == true && (map[i][j].getInfected() == true || map[i][j+1].getInfected() == true)) {
//								if(map[i][j+1].getInfected() == true) {
//									map[i][j].becomeInfected();
//									System.out.println(i + ", " + (j+1) + "\tinfected " + i + ", " + j + "\tj != 0");
//								}else{
//									map[i][j+1].becomeInfected();
//									System.out.println(i + ", " + j + "\tinfected " + i + ", " + (j+1) + "\tj != 0");
//								}
//								//System.out.println(i + ", " + j + "\tinfected " + i + ", " + (j+1) + "\tj != 0");
//							}
//						} catch (IndexOutOfBoundsException e) {
//							
//						}
//					}
//				}
//			}
			
			
			
			
		
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				infectedMap[i][j] = map[i][j].getInfected();
//				if(map[i][j].getInfected() == true) {
//					
//				}
			}
		}
//		for(int i = 0; i < map.length; i++) {
//			for(int j = 0; j < map[i].length; j++) {
//				System.out.println(i + ", " + j + " - " + infectedMap[i][j]);
//			}
//		}
		
		
		//now I need to make sure this works properly
		for(int i = 0; i < infectedMap.length; i++) {
			for(int j = 0; j < infectedMap[i].length; j++) {
				if (infectedMap[i][j] == false) {
					return false;
				}
			}
		}
		
		return true;
	}
	
//	public boolean allFalseArray(boolean[] bRay) {
//		for(boolean bool: bRay) {
//			if (bool == true) {
//				return false;
//			}
//		}
//		return true;
//	}
	
	public boolean[] infectedAdjacent(Tile[][] map, int row, int col) {
		boolean[] returnArray = {false, false, false, false};
		//looks like {top, bottom, left, right}
		if(map[row][col].getInfected() == true) {
			//i dont need to mess with already infected tiles, so this
			//System.out.println("Tile Already Infected");
			return returnArray;
		}
		//i could combine these but I dont want it to stop and mess up if the catch happens
		try {
			//check above
			if(map[row-1][col].getInfected() == true) {
				returnArray[0] = true;
				//System.out.println("Top Tile Infected");
			}
		} catch(IndexOutOfBoundsException e) {
		}
		try {
			//check below
			if(map[row+1][col].getInfected() == true) {
				returnArray[1] = true;
				//System.out.println("Bottom Tile Infected");
			}
		} catch(IndexOutOfBoundsException e) {
		}
		try {
			//check left
			if(map[row][col-1].getInfected() == true) {
				returnArray[2] = true;
				//System.out.println("Left Tile Infected");
			}
		} catch(IndexOutOfBoundsException e) {
		}
		try {
			//check right
			if(map[row][col+1].getInfected() == true) {
				returnArray[3] = true;
				//System.out.println("Right Tile Infected");
			}
		} catch(IndexOutOfBoundsException e) {
		}
		return returnArray;
	}
	
	
	public boolean[] infectedAdjacent2(Tile[][] map, int row, int col) {
		boolean[] returnArray = {false, false, false, false};
		//looks like {top, bottom, left, right}
		if(map[row][col].infected2 == true) {
			//i dont need to mess with already infected tiles, so this
			//System.out.println("Tile Already Infected");
			return returnArray;
		}
		//i could combine these but I dont want it to stop and mess up if the catch happens
		try {
			//check above
			if(map[row-1][col].infected2 == true) {
				returnArray[0] = true;
				//System.out.println("Top Tile Infected");
			}
		} catch(IndexOutOfBoundsException e) {
		}
		try {
			//check below
			if(map[row+1][col].infected2 == true) {
				returnArray[1] = true;
				//System.out.println("Bottom Tile Infected");
			}
		} catch(IndexOutOfBoundsException e) {
		}
		try {
			//check left
			if(map[row][col-1].infected2 == true) {
				returnArray[2] = true;
				//System.out.println("Left Tile Infected");
			}
		} catch(IndexOutOfBoundsException e) {
		}
		try {
			//check right
			if(map[row][col+1].infected2 == true) {
				returnArray[3] = true;
				//System.out.println("Right Tile Infected");
			}
		} catch(IndexOutOfBoundsException e) {
		}
		return returnArray;
	}
	

	
	
	
	public boolean checkMapv3(Tile[][] map) {
		//need to print out how each piece connects, try to figure it out
		//when its done, make it able to remove loose ends not on edges
		boolean[][] infectedMap = new boolean[map.length][map[0].length];
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				infectedMap[i][j] = false;
			}
		}
		
		map[0][0].becomeInfected();
		
		
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				//check above
				//[i+1][j]
				try {
					if(map[i][j].getTopSlot() == true && map[i-1][j].getBottomSlot() == true && (map[i][j].getInfected() == true || map[i-1][j].getInfected() == true)) {
						//System.out.println("Tile " + i + ", " + j + " connects to Tile " + (i-1) + ", " + j + "\t line 360");
						if(map[i-1][j].getInfected() == true) {
							map[i][j].becomeInfected();
						}else{
							map[i-1][j].becomeInfected();
						}
					}
				} catch (IndexOutOfBoundsException e) {
					//System.out.println("Error on check above");
				}
				//check below
				try {
					if(map[i][j].getBottomSlot() == true && map[i+1][j].getTopSlot() == true && (map[i][j].getInfected() == true || map[i+1][j].getInfected() == true)) {
						//System.out.println("Tile " + i + ", " + j + " connects to Tile " + (i+1) + ", " + j + "\t line 373");
						if(map[i+1][j].getInfected() == true) {
							map[i][j].becomeInfected();
						}else{
							map[i+1][j].becomeInfected();
						}
					}
				} catch (IndexOutOfBoundsException e) {
					//System.out.println("Error on check below");
				}
				//check left
				try {
					if(map[i][j].getLeftSlot() == true && map[i][j-1].getRightSlot() == true && (map[i][j].getInfected() == true || map[i][j-1].getInfected() == true)) {
						//System.out.println("Tile " + i + ", " + j + " connects to Tile " + i + ", " + (j-1) + "\t line 386");
						if(map[i][j-1].getInfected() == true) {
							map[i][j].becomeInfected();
						}else{
							map[i][j-1].becomeInfected();
						}
					}
				} catch (IndexOutOfBoundsException e) {
					//this will nullify stuffs
					//System.out.println("Error on check left");
				}

				//check right
				try {
					if(map[i][j].getRightSlot() == true && map[i][j+1].getLeftSlot() == true && (map[i][j].getInfected() == true || map[i][j+1].getInfected() == true)) {
						//System.out.println("Tile " + i + ", " + j + " connects to Tile " + i + ", " + (j+1) + "\t line 401");
						if(map[i][j+1].getInfected() == true) {
							map[i][j].becomeInfected();
						}else{
							map[i][j+1].becomeInfected();
						}
					}
				} catch (IndexOutOfBoundsException e) {
					//System.out.println("Error on check right");
				}
			}
		}

			
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				infectedMap[i][j] = map[i][j].getInfected();
//				if(map[i][j].getInfected() == true) {
//					
//				}
			}
		}
//		for(int i = 0; i < map.length; i++) {
//			for(int j = 0; j < map[i].length; j++) {
//				System.out.println(i + ", " + j + " - " + infectedMap[i][j]);
//			}
//		}
		
		
		//now I need to make sure this works properly
		for(int i = 0; i < infectedMap.length; i++) {
			for(int j = 0; j < infectedMap[i].length; j++) {
				if (infectedMap[i][j] == false) {
					//System.out.println("----------MAP FAILED----------");
					return false;
				}
			}
		}
		
		return true;
	}
	
	public boolean checkMapNoInfect(Tile[][] map) {
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				if(map[i][j].getInfected() == false) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean checkMapInfect2(Tile[][] map) {
		boolean[][] infectedMap = new boolean[map.length][map[0].length];
		//wipes map to ensure results
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				infectedMap[i][j] = false;
			}
		}
		
		map[0][0].infected2 = true;
		
		
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				//check above
				//[i+1][j]
				try {
					if(map[i][j].getTopSlot() == true && map[i-1][j].getBottomSlot() == true && (map[i][j].infected2 == true || map[i-1][j].infected2 == true)) {
						//System.out.println("Tile " + i + ", " + j + " connects to Tile " + (i-1) + ", " + j + "\t line 360");
						if(map[i-1][j].infected2 == true) {
							map[i][j].infected2 = true;
						}else{
							map[i-1][j].infected2 = true;
						}
					}
				} catch (IndexOutOfBoundsException e) {
					//System.out.println("Error on check above");
				}
				//check below
				try {
					if(map[i][j].getBottomSlot() == true && map[i+1][j].getTopSlot() == true && (map[i][j].infected2 == true || map[i+1][j].infected2 == true)) {
						//System.out.println("Tile " + i + ", " + j + " connects to Tile " + (i+1) + ", " + j + "\t line 373");
						if(map[i+1][j].infected2 == true) {
							map[i][j].infected2 = true;
						}else{
							map[i+1][j].infected2 = true;
						}
					}
				} catch (IndexOutOfBoundsException e) {
					//System.out.println("Error on check below");
				}
				//check left
				try {
					if(map[i][j].getLeftSlot() == true && map[i][j-1].getRightSlot() == true && (map[i][j].infected2 == true || map[i][j-1].infected2 == true)) {
						//System.out.println("Tile " + i + ", " + j + " connects to Tile " + i + ", " + (j-1) + "\t line 386");
						if(map[i][j-1].infected2 == true) {
							map[i][j].infected2 = true;
						}else{
							map[i][j-1].infected2 = true;
						}
					}
				} catch (IndexOutOfBoundsException e) {
					//this will nullify stuffs
					//System.out.println("Error on check left");
				}

				//check right
				try {
					if(map[i][j].getRightSlot() == true && map[i][j+1].getLeftSlot() == true && (map[i][j].infected2 == true || map[i][j+1].infected2 == true)) {
						//System.out.println("Tile " + i + ", " + j + " connects to Tile " + i + ", " + (j+1) + "\t line 401");
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

			
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				infectedMap[i][j] = map[i][j].infected2;
//				if(map[i][j].getInfected() == true) {
//					
//				}
			}
		}
//		for(int i = 0; i < map.length; i++) {
//			for(int j = 0; j < map[i].length; j++) {
//				System.out.println(i + ", " + j + " - " + infectedMap[i][j]);
//			}
//		}
		
		
		//now I need to make sure this works properly
		for(int i = 0; i < infectedMap.length; i++) {
			for(int j = 0; j < infectedMap[i].length; j++) {
				if (infectedMap[i][j] == false) {
					//System.out.println("----------MAP FAILED----------");
					return false;
				}
			}
		}
		
		return true;
	}
	
	
	
}
