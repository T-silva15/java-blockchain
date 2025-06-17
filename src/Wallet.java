import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.HashMap;
import java.util.ArrayList;

public class Wallet {

    public PrivateKey privateKey; // Used to sign transactions
    public PublicKey publicKey; // Serves as the address

    public HashMap<String, TransactionOutput> UTXOs = new HashMap<String, TransactionOutput>(); // only UTXOs owned by this wallet.

    public Wallet() {
        generateKeyPair();
    }

    public void generateKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
			// Initialize the key generator and generate a KeyPair
			keyGen.initialize(ecSpec, random);   //256 bytes provides an acceptable security level
	        	KeyPair keyPair = keyGen.generateKeyPair();
	        	// Set the public and private keys from the keyPair
	        	privateKey = keyPair.getPrivate();
	        	publicKey = keyPair.getPublic();
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

    // Calculate and return the balance of this wallet
    public float getBalance() {
        float total = 0;
        for (String key : App.UTXOs.keySet()) {
            TransactionOutput UTXO = App.UTXOs.get(key);
            if (UTXO.isMine(publicKey)) { // if output belongs to me (if coins belong to me)
                UTXOs.put(UTXO.id, UTXO); // add it to our list of unspent transactions.
                total += UTXO.value;
            }
        }
        return total;
    }

    // Generate transaction and return it
    public Transaction sendFunds(PublicKey recipient, float value) {
        if (getBalance() < value) { // gather balance and check funds.
            System.out.println("#Not Enough funds to send transaction. Transaction Discarded.");
            return null;
        }
        // create array list of inputs
        ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();

        float total = 0;
        for (String key : UTXOs.keySet()) {
            TransactionOutput UTXO = UTXOs.get(key);
            total += UTXO.value;
            inputs.add(new TransactionInput(UTXO.id));
            if (total > value) break;
        }

        Transaction newTransaction = new Transaction(publicKey, recipient, value, inputs);
        newTransaction.generateSignature(privateKey);

        for (TransactionInput input : inputs) {
            UTXOs.remove(input.transactionOutputId);
        }
        return newTransaction;
    }

}
