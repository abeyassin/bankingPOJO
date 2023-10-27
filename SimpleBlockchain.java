import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



// Define a Student class
class BankInfo {
    private String name;
    private long acctnumber;
    private long routingnumber;
    private double money;



    public BankInfo(String name, long acctnumber, int routingnumber, double money) {
        this.name = name;
        this.acctnumber = acctnumber;
        this.routingnumber = routingnumber;
        this.money = money;
    }



    // Getters for student properties
    public String getName() {
        return name;
    }



    public long getAcctnumber() {
        return acctnumber;
    }



    public long getRoutingnumber() {
        return routingnumber;
    }

    public double getMoney() {
        return money;

    }
}




// Define a Block class
class Block {
    private int index;
    private long timestamp;
    private String previousHash;
    private String hash;
    private BankInfo bankInfo;



    public Block(int index, String previousHash,BankInfo bankInfo) {
        this.index = index;
        this.timestamp = new Date().getTime();
        this.previousHash = previousHash;
        this.bankInfo = bankInfo;
        this.hash = calculateHash();
    }



    // Calculate the hash of the block
    public String calculateHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            int nonce = 0;
            String input;

            while (true) {
                input = index + timestamp + previousHash + bankInfo.getName() + bankInfo.getAcctnumber() + bankInfo.getRoutingnumber() + bankInfo.getMoney() + nonce;
                byte[] hashBytes = digest.digest(input.getBytes("UTF-8"));
                StringBuilder hexString = new StringBuilder();

                for (byte b : hashBytes) {
                    String hex = Integer.toHexString(0xff & b);
                    if (hex.length() == 1) hexString.append('0');
                    hexString.append(hex);
                }

                String hash = hexString.toString();

                // Check if the hash starts with "00"
                if (hash.startsWith("445")) {
                    return hash;
                }

                // If not, increment the nonce and try again
                nonce++;
            }
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }



    // Getters
    public int getIndex() {
        return index;
    }



    public long getTimestamp() {
        return timestamp;
    }



    public String getPreviousHash() {
        return previousHash;
    }



    public String getHash() {
        return hash;
    }



    public BankInfo getBankInfo() {
        return bankInfo;
    }
}



// Define a Blockchain class
class Blockchain {
    private List<Block> chain;



    // Constructor
    public Blockchain() {
        chain = new ArrayList<Block>();
        // Create the genesis block (the first block in the chain)
        chain.add(new Block(0, "0", new BankInfo("Genesis Block", 0000000000, 0000000000, 0.00)));
    }



    // Add a new block to the blockchain
    public void addBankInfo(BankInfo bankInfo) {
        Block previousBlock = chain.get(chain.size() - 1);
        Block newBlock = new Block(previousBlock.getIndex() + 1, previousBlock.getHash(), bankInfo);
        chain.add(newBlock);
    }



    public void printBlockchain() {
        for (Block block : chain) {
            System.out.println("Block #" + block.getIndex());
            System.out.println("Timestamp: " + block.getTimestamp());
            System.out.println("Previous Hash: " + block.getPreviousHash());
            System.out.println("Hash: " + block.getHash());
            System.out.println("Name: " + block.getBankInfo().getName());
            System.out.println("Account Number: " + block.getBankInfo().getAcctnumber());
            System.out.println("Routing Number: " + block.getBankInfo().getRoutingnumber());
            System.out.println("Money in account: $" + block.getBankInfo().getMoney());
            System.out.println();
        }
    }
}



public class SimpleBlockchain {
    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();
        // DatabaseManager dbManager = new DatabaseManager();

        // Create student objects and add them to the blockchain
       BankInfo bankInfo1 = new BankInfo("Joe Oakes", 7458623695l, 1485563695, 97885.00);
       BankInfo bankInfo2 = new BankInfo("Branden Vasquez", 7856325956l, 1485478562, 2445.00);
       BankInfo bankInfo3 = new BankInfo("Su Gon Deez Jr.", 9685446844l, 1586963252, 541233.00);



        blockchain.addBankInfo(bankInfo1);
        blockchain.addBankInfo(bankInfo2);
        blockchain.addBankInfo(bankInfo3);



        // Print the blockchain
        blockchain.printBlockchain();
    }
}