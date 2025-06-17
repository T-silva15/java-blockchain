import java.util.ArrayList;
import java.util.Date;

public class Block {
    
    public String hash;
    public String previousHash;
    public String data;
    public long timeStamp; // milliseconds
    public int nonce;

    public String merkleRoot;
    public ArrayList<Transaction> transactions = new ArrayList<Transaction>(); 

    //Block Constructor.  
	public Block(String previousHash ) {
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		
		this.hash = calculateHash(); //Making sure we do this after we set the other values.
	}
	
	//Calculate new hash based on blocks contents
	public String calculateHash() {
		String calculatedhash = StringUtil.applySha256( 
				previousHash +
				Long.toString(timeStamp) +
				Integer.toString(nonce) + 
				merkleRoot
				);
		return calculatedhash;
	}
	
	//Increases nonce value until hash target is reached.
	public void mineBlock(int difficulty) {
		merkleRoot = StringUtil.getMerkleRoot(transactions);
		String target = StringUtil.getDificultyString(difficulty); 
		System.out.println("Mining block with target: " + target + " (difficulty: " + difficulty + ")");
		
		long startTime = System.currentTimeMillis();
		while(!hash.substring( 0, difficulty).equals(target)) {
			nonce ++;
			hash = calculateHash();
			
			// Progress indicator every 100000 attempts
			if(nonce % 100000 == 0) {
				System.out.println("Mining attempt: " + nonce + ", current hash: " + hash.substring(0, Math.min(10, hash.length())) + "...");
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Block Mined!!! : " + hash + " (took " + (endTime - startTime) + " ms, " + nonce + " attempts)");
	}
	
	//Add transactions to this block
	public boolean addTransaction(Transaction transaction) {
		//process transaction and check if valid, unless block is genesis block then ignore.
		if(transaction == null) return false;		
		if((!previousHash.equals("0"))) {
			if((transaction.processTransaction() != true)) {
				System.out.println("Transaction failed to process. Discarded.");
				return false;
			}
		}
		transactions.add(transaction);
		System.out.println("Transaction Successfully added to Block");
		return true;
	}
}
