

import java.util.ArrayList;
import java.util.HashMap;



public class PathMap {

  public PathFindingOnSquaredGrid pathfinder;
  public Pose2d curPose;
  public Pose2d endPose;
  // public Pose2d[] Obstacles = new Pose2d[2];
  public ArrayList<Pose2d> Obstacles = new ArrayList<>();
  public Node node;
  public boolean[][] matrix;
  public double[] gridSize = new double[2];
  public int obstaclenum = 0;
  public int n;
  public int Ai;
  public int Aj;
  public int Bi;
  public int Bj;
  public HashMap<String, Pose2d> namedPath = new HashMap<>();
  public ArrayList<Pose2d> Path = new ArrayList<>();


  public void getPose(Pose2d startpoint, Pose2d endpoint) {
    curPose = startpoint;
    endPose = endpoint;
    Ai = Math.toIntExact(Math.round(((curPose.getTranslation().getX())/ 2.25)*n));
    Aj = Math.toIntExact(Math.round(((curPose.getTranslation().getY()) / 4.5)*n));
    Bi = Math.toIntExact(Math.round(((endPose.getTranslation().getX())/ 2.25)*n));
    Bj = Math.toIntExact(Math.round(((endPose.getTranslation().getY())/ 4.5)*n));
  }

  public void getObstacles(Pose2d Obstacle) {
    Obstacles.add(obstaclenum, Obstacle);
    obstaclenum++;
  }

  public void generateGrid(int size) {
    n = size;
    gridSize[1] = 4.5 / n;
    gridSize[0] = 2.25 / n;
    matrix = new boolean[n][n];
  }
  
  public boolean[][] generateMap() {
    
    for (int x = 0; x < n; x++) {
      for (int y = 0; y < n; y++) {
        matrix[x][y] = true;
      }
    }

    for (int i = 0; i < Obstacles.size(); i++) {
      int x = Math.toIntExact(Math.round((Obstacles.get(i).getTranslation().getX()/ 2.25)*n));
      int y = Math.toIntExact(Math.round((Obstacles.get(i).getTranslation().getY() / 4.5)*n));

      //larger x boundary because rectangular grid boxes
      for (int xboundary = 0; xboundary < n/10; xboundary++) {
        // matrix[x + xboundary][y] = false;
        // matrix[x - xboundary][y] = false;
        for (int yboundary = 0; yboundary < n/30; yboundary++) {
          matrix[x + xboundary][y + yboundary] = false;
          matrix[x + xboundary][y - yboundary] = false;
          matrix[x - xboundary][y + yboundary] = false;
          matrix[x - xboundary][y - yboundary] = false;
        }
      }


    }
    return matrix;
  }
  
  public void calculate() {

    pathfinder.generateHValue(matrix, Ai, Aj, Bi, Bj, n, 1, 100, false, 1);

    for (int i = 0; i <= pathfinder.pathList.size(); i++) {
      node = pathfinder.pathList.get(i);
      namedPath.put("Waypoint" + i, new Pose2d(node.x * gridSize[0], node.y * gridSize[1], new Rotation2d(0)));
      Path.add(i, new Pose2d(node.x * gridSize[0], node.y * gridSize[1], new Rotation2d(0)));
    }

  }
  
  public void Reduce() {
    
  }

}