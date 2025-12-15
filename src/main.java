package src;

public class main {

    public static void main(String[] args) {
        System.out.println("Hello World");
        try {
            // Create a new blockchain
            Blockchain blockchain = new Blockchain();

            // Add a block to the blockchain
            blockchain.addBlock("This is transaction 1");
            blockchain.addBlock("This is transaction 2");

            // Print the chain
            blockchain.printChain();

            // Check if the blockchain is valid
            if (blockchain.isValid()) {
                System.out.println("Blockchain is valid");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}