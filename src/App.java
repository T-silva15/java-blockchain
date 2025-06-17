import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class App {

    public static ArrayList<Block> blockchain = new ArrayList<Block>(); // List to hold the blockchain

    public static int chainSize = 5; // Size of the blockchain

    public static int difficulty = 6; // Difficulty level for mining
    
    public static void main(String[] args) throws Exception {

        for (int i = 0; i < chainSize; i++) {
            if (i == 0) {
                // The first block is the genesis block, so we initialize it with empty data and previous hash
                blockchain.add(new Block("Genesis Block", "0"));
            } else
                // For subsequent blocks, we initialize them with empty data and the hash of the previous block
                blockchain.add(new Block("Block Number: " + Integer.toString(i), blockchain.get(i - 1).hash));
            
            // Mine the blocks
            System.out.println("Trying to Mine block " + Integer.toString(i) + "...");
            blockchain.get(i).mineBlock(difficulty);
            if (i == 0) {
                System.out.println("Block " + Integer.toString(i) + " mined successfully!");
            } else {
                long timeDifference = (blockchain.get(i).timeStamp - blockchain.get(i-1).timeStamp)/1000;
                System.out.println("Block " + Integer.toString(i) + " mined successfully in " + timeDifference + " seconds.");
            }
        }

        // Validate the blockchain
        System.out.println("\nBlockchain valid: " + isChainValid());

        // Gson library to convert blockchain to JSON
        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println("\nBlockchain as JSON:");
        System.out.println(blockchainJson);
        
    }

    // Method to validate the blockchain
    // Checks if the hashes are correct and if the previous hash of each block matches the current, as well as ensuring that the timestamp of each block is greater than the previous one.
    // Returns true if the blockchain is valid, false otherwise.
    public static Boolean isChainValid() {
        for (int i = 1; i < blockchain.size(); i++) {
            Block currentBlock = blockchain.get(i);
            Block previousBlock = blockchain.get(i - 1);

            // Create a string of zeros based on the difficulty level
            // We subtract 1 from the difficulty to create the target string
            // This is because we replace the \0 character with a zero, so we need one <less zero than the difficulty level
            String hashTarget = new String(new char[difficulty - 1]).replace('\0', '0');

            // Check if the current block's hash is equal to the calculated hash
            // If the hashes do not match, the blockchain is invalid
            if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
                System.out.println("Current Hashes not equal");
                return false;
            }

            // Check if the previous hash of the current block matches the hash of the previous block
            // If the previous hash does not match, the blockchain is invalid
            if (!currentBlock.previousHash.equals(previousBlock.hash)) {
                System.out.println("Previous Hashes not equal");
                return false;
            }

            // Check if the current block's timestamp is greater than the previous block's timestamp
            // If the current block's timestamp is less than or equal to the previous block's timestamp
            if (currentBlock.timeStamp < previousBlock.timeStamp) {
                System.out.println("Current timestamp is not greater than previous block's timestamp");
                return false;
            }

            // Check if the block is mined correctly by comparing the hash with the target
            // If the hash does not start with the required number of zeros, the block is not mined correctly
            if (currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
                System.out.println("Block " + Integer.toString(i) + " not mined correctly");
                return false;
            }
        }

        return true;
    }
}
