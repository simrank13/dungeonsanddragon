/**
 * this class implements algorithm to compute shortest path from initial chamber to exit
 *
 */
public class FindShortestPath {
	/**
	 * method checks if the current chamber is the exit
	 * @param currChamber
	 * @return exit chamber
	 */
	private static boolean checkingForExit(Hexagon currChamber) {
		// return true if the current chamber is type exit otherwise return false
		return currChamber.isExit();
	}

	/**
	 * method checks if current chamber has dragon in it 
	 * @param currChamber
	 * @return chamber with dragon
	 */
	private static boolean checkingForDragon(Hexagon currChamber) {
		// return true if current chamber is type dragon otherwise return false
		return currChamber.isDragon();
	}

	/**
	 * method checks if any of the neighbouring chambers has a dragon
	 * @param currChamber
	 * @return neighbour chambers with dragon
	 */
	private static boolean checkingNeighboursForDragon(Hexagon currChamber) {
		// initialized boolean variable as neighbouring chambers with dragon as false
		boolean neighbourHasDragon = false;
		// iterate through the 6 neighbour chambers so index 0 to 5 and if the current chamber does not have a neighbour then continue
		for (int i = 0; i < 6; ++i) {
			if (currChamber.getNeighbour(i) == null) {
				continue;
			}
			// and if the  neighbouring chamber has a dragon update that the boolean variable representing that the neighbour has dragon as true
			// since the neighbour chamber is of type dragon and then skip to next iteration
			if (currChamber.getNeighbour(i).isDragon()) {
				neighbourHasDragon = true;
				continue;
			}
		}
		// return if the neighbour has a dragon (true) or not (false)
		return neighbourHasDragon;
	}
	/**
	 * method determines what is the length of the shortest possible path
	 * @param dungeon
	 * @return the path length
	 */
	private static int getLengthOfShortestPath(Dungeon dungeon) {
		// initialize the length of the path as 1 since the path includes the starting chamber
		// create a hexagon object representing the dungeon object is the exit 
		// while predecessor chamber of current chamber isnt null, increment the path length by 1 and get the predecessor chamber of the current chamber
		int lengthOfShortestPath = 1;
		Hexagon currChamber = dungeon.getExit();
		while (currChamber.getPredecessor() != null) {
			lengthOfShortestPath++;
			currChamber = currChamber.getPredecessor();
		}
		// then return the path length
		return lengthOfShortestPath;
	}

	/**
	 * this method updates the distance to start and predecessor chamber of neighbour chambers of current chambers
	 * @param prioQueue
	 * @param dungeon
	 * @param currChamber
	 */
	private static void updateDistanceOfChamber(DLPriorityQueue<Hexagon> prioQueue, Dungeon dungeon, Hexagon currChamber) {
		// iterate through the 6 neighbour chambers so index 0 to 5 and if the current chamber does not have a neighbour then continue
		//if the neighbour chamber is null or is type wall or marked as dequeued then skip to next iteration
		for (int i = 0; i < 6; i++) {
			if (currChamber.getNeighbour(i) == null || currChamber.getNeighbour(i).isWall() || currChamber.getNeighbour(i).isMarkedDequeued()) {
				continue;
			}

			// initalize variable D and let it equal 1 + the distance from current to inital chamber
			double D = 1 + currChamber.getDistanceToStart();

			// if the distance of neighbour to initial chamber is larger than D then set distance of neighbour to inital chamber to D
			// and set the current as the predecessor of neighbour 
			if (currChamber.getNeighbour(i).getDistanceToStart() > D) {
				currChamber.getNeighbour(i).setDistanceToStart((int) D);
				currChamber.getNeighbour(i).setPredecessor(currChamber);

				// if the neighbour is marked as enqueued and the distance from neighbour to initial chamber was modified 
				//then update priority that neighbour has in priority queue  
				// this means that the new priority of neighbour is distance from neighbour to inital chamber plus distance form neighbour to exit 
				if (currChamber.getNeighbour(i).isMarkedEnqueued()) {
					prioQueue.updatePriority(currChamber.getNeighbour(i), D + currChamber.getNeighbour(i).getDistanceToExit(dungeon));

					// otherwise if neighbour is not marked as enqueued then add it to thr queue with priority equal to its distance to initial chamber plus its distance to exit
					// then mark neighbour as enqueued
				} else if(!currChamber.getNeighbour(i).isMarkedEnqueued()){
					prioQueue.add(currChamber.getNeighbour(i), D + currChamber.getNeighbour(i).getDistanceToExit(dungeon));
					currChamber.getNeighbour(i).markEnqueued();
				}
			}
		}
	}
	// main method tkes input file and creates dungeon object for the file
	public static void main(String[] args) {
		// try block to execute code that might throw exception 
		try {
			// check if the input file is provided argument otherwise if argument not provided then throws exception that the input file wasnt specified
			if (args.length < 1) throw new Exception("No input file specified");

			// stores name of input file in variable dungeonFIleName 
			String dungeonFileName = args[0];
			// creates dungeon object for input file suggested
			Dungeon dungeon = new Dungeon(dungeonFileName); 

			// create empty priority queue that will record the chamber distance from start
			DLPriorityQueue<Hexagon> prioQueue = new DLPriorityQueue<Hexagon>(); 
			// create hexagon object to get the chamber from start from dungeon 
			Hexagon beginningChamber = dungeon.getStart(); 

			// add the chamber from start to priority queue and mark it as enqueued
			prioQueue.add(beginningChamber, 0);
			beginningChamber.markEnqueued();

			// while the priority queue isnt empty and exit not found then remove chamber with smallest priority from priority queue and mark as dequeued
			while (!prioQueue.isEmpty()) {
				Hexagon currChamber = prioQueue.removeMin();
				currChamber.markDequeued();

				// check if the current chamber and if current chamber is exit then get the length of the shortest path of the dungeon object
				// then print out to the screen the length of the path 
				if (checkingForExit(currChamber)) {
					int lengthOfShortestPath = getLengthOfShortestPath(dungeon);
					System.out.println("Path of length " + lengthOfShortestPath + " found");
					// returning to screen which will cause program to end once shortest path is found or red line is made
					return;
				}
				// check if the current chamber has a dragon and if so then loop will be immediately terminated or "break"
				if (checkingForDragon(currChamber)) {
					break;
				}
				// check for neighbour having dragons and if so then skip to next iteration
				if (checkingNeighboursForDragon(currChamber)) {
					continue;
				}
				// update the distance of the chamber 
				updateDistanceOfChamber(prioQueue, dungeon, currChamber);
			}
			// otherwise if no length then print no path found to the screen 
			System.out.println("No path found");

			// catch the exception throught and print the message of the exception 
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}



}
