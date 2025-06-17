
import java.util.ArrayList;
import java.security.*;

public class Transaction {
    
    public String transactionId; // Unique identifier for the transaction
    public PublicKey sender; // Public key of the sender
    public PublicKey recipient; // Public key of the recipient
    public float value; // Amount being transferred
    public byte[] signature; // Digital signature of the transaction

    public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>(); // List of inputs for the transaction
    public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>(); // List of outputs

	private static int sequence = 0; // a rough count of how many transactions have been generated. 


    // Constructor for basic transaction
    public Transaction(PublicKey from, PublicKey to, float value) {
        this.sender = from;
        this.recipient = to;
        if (value <= 0) {
            throw new IllegalArgumentException("Transaction value must be greater than zero.");
        }
        else {
            this.value = value;
        }
        this.transactionId = calculateHash();
    }

    // Constructor with inputs for proper UTXO transaction
    public Transaction(PublicKey from, PublicKey to, float value, ArrayList<TransactionInput> inputs) {
        this.sender = from;
        this.recipient = to;
        if (value <= 0) {
            throw new IllegalArgumentException("Transaction value must be greater than zero.");
        }
        else {
            this.value = value;
        }
        this.inputs = inputs;
        this.transactionId = calculateHash();
    }


    private String calculateHash() {
		sequence++; // Increase the sequence to avoid 2 identical transactions having the same hash
		return StringUtil.applySha256(
				StringUtil.getStringFromKey(sender) +
				StringUtil.getStringFromKey(recipient) +
				Float.toString(value) + sequence
				);
	}

    // Signs all the data we dont wish to be tampered with.
    public void generateSignature(PrivateKey privateKey) {
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(recipient) + Float.toString(value)	;
        signature = StringUtil.applyECDSASig(privateKey,data);		
    }

    // Verifies the data we signed hasnt been tampered with
    public boolean verifiySignature() {
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(recipient) + Float.toString(value)	;
        return StringUtil.verifyECDSASig(sender, data, signature);
    }

    // Returns true if new transaction could be created
    public boolean processTransaction() {

        if (verifiySignature() == false) {
            System.out.println("Transaction Signature failed to verify");
            return false;
        }

        // Gather transaction inputs (make sure they are unspent):
        for (TransactionInput i : inputs) {
            i.UTXO = App.UTXOs.get(i.transactionOutputId);
        }

        // Check if transaction is valid:
        if (getInputsValue() < App.minimumTransaction) {
            System.out.println("Transaction Inputs to small: " + getInputsValue());
            return false;
        }

        // Generate transaction outputs:
        float leftOver = getInputsValue() - value; // get value of inputs then the left over change:
        transactionId = calculateHash();
        outputs.add(new TransactionOutput(this.recipient, value, transactionId)); // send value to recipient
        outputs.add(new TransactionOutput(this.sender, leftOver, transactionId)); // send the left over 'change' back to sender

        // Add outputs to Unspent list
        for (TransactionOutput o : outputs) {
            App.UTXOs.put(o.id, o);
        }

        // Remove transaction inputs from UTXO lists as spent:
        for (TransactionInput i : inputs) {
            if (i.UTXO == null) continue; // if Transaction can't be found skip it 
            App.UTXOs.remove(i.UTXO.id);
        }

        return true;
    }

    // Returns sum of inputs(UTXOs) values
    public float getInputsValue() {
        float total = 0;
        for (TransactionInput i : inputs) {
            if (i.UTXO == null) continue; // if Transaction can't be found skip it 
            total += i.UTXO.value;
        }
        return total;
    }

    // Returns sum of outputs:
    public float getOutputsValue() {
        float total = 0;
        for (TransactionOutput o : outputs) {
            total += o.value;
        }
        return total;
    }
}
