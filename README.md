# 🔗 Java Blockchain Implementation

A fully functional blockchain implementation in Java featuring UTXO transactions, digital signatures, proof-of-work mining, and comprehensive balance validation.

![Java](https://img.shields.io/badge/Java-11+-orange.svg)
![License](https://img.shields.io/badge/License-MIT-blue.svg)
![Build](https://img.shields.io/badge/Build-Passing-green.svg)

## 📋 Overview

This project implements a complete blockchain system from scratch in Java, demonstrating core blockchain concepts including:

- **UTXO (Unspent Transaction Output) Model**: Similar to Bitcoin's transaction model
- **Digital Signatures**: ECDSA-based transaction signing and verification
- **Proof of Work Mining**: Adjustable difficulty mining algorithm
- **Transaction Validation**: Comprehensive balance checking and transaction processing
- **Merkle Tree**: Transaction integrity verification
- **Wallet Management**: Key generation and balance tracking

**Technology Stack:**
- Java 11+
- BouncyCastle Cryptography Library
- Google Gson (JSON serialization)
- SHA-256 Hashing
- ECDSA Digital Signatures

## ✨ Features

### Core Blockchain Features
- ✅ **Block Creation and Mining**: Proof-of-work consensus with adjustable difficulty
- ✅ **Transaction Processing**: Full UTXO-based transaction system
- ✅ **Digital Signatures**: Cryptographic transaction signing and verification
- ✅ **Balance Validation**: Prevents double-spending and insufficient fund transactions
- ✅ **Blockchain Validation**: Complete chain integrity verification
- ✅ **Merkle Root**: Transaction integrity using Merkle tree structure

### Wallet Features
- ✅ **Key Pair Generation**: ECDSA public/private key creation
- ✅ **Balance Calculation**: Real-time UTXO-based balance tracking
- ✅ **Transaction Creation**: Automated input selection and change calculation
- ✅ **Fund Validation**: Pre-transaction balance verification

### Security Features
- ✅ **Cryptographic Hashing**: SHA-256 for block and transaction hashing
- ✅ **Digital Signatures**: ECDSA signatures prevent transaction tampering
- ✅ **Input Validation**: Comprehensive transaction input verification
- ✅ **Chain Validation**: Multi-level blockchain integrity checks

## 📁 Project Structure

```
blockchain-java/
├── lib/
│   └── gson-2.6.2.jar           # JSON serialization library
├── src/
│   ├── Blockchain.java          # Main application and blockchain management
│   ├── Block.java               # Block structure and mining logic
│   ├── Transaction.java         # Transaction processing and validation
│   ├── TransactionInput.java    # Transaction input (UTXO references)
│   ├── TransactionOutput.java   # Transaction output (new UTXOs)
│   ├── Wallet.java              # Wallet management and key operations
│   └── StringUtil.java          # Cryptographic utilities and helpers
├── JAR_LIBRARY_GUIDE.md         # Library integration guide
└── README.md                    # This file
```

### Class Responsibilities

| Class | Purpose |
|-------|---------|
| `Blockchain` | Main application, blockchain storage, and validation logic |
| `Block` | Block structure, mining algorithm, and transaction storage |
| `Transaction` | Transaction creation, signing, and processing |
| `TransactionInput` | References to unspent transaction outputs |
| `TransactionOutput` | New UTXOs created by transactions |
| `Wallet` | Key management, balance calculation, and transaction creation |
| `StringUtil` | Cryptographic utilities (hashing, signatures, encoding) |

## 🚀 Getting Started

### Prerequisites

- **Java 11 or higher**
- **BouncyCastle Provider** (included in project dependencies)

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd blockchain-java
   ```

2. **Compile the project**
   ```bash
   javac -cp "lib/*:src" src/*.java
   ```

3. **Run the blockchain**
   ```bash
   java -cp "lib/*:src" Blockchain
   ```

### Quick Start Commands

```bash
# Navigate to project directory
cd /path/to/blockchain-java

# Compile all Java files
javac -cp "lib/*:src" src/*.java

# Run the blockchain application
java -cp "lib/*:src" Blockchain

# Combined compile and run
javac -cp "lib/*:src" src/*.java && java -cp "lib/*:src" Blockchain
```

## 💡 Usage Examples

### Basic Blockchain Operations

The application demonstrates several key blockchain operations:

```java
// Create wallets
Wallet walletA = new Wallet();
Wallet walletB = new Wallet();

// Check balance
float balance = walletA.getBalance();

// Send funds (with automatic balance validation)
Transaction transaction = walletA.sendFunds(walletB.publicKey, 40f);

// Mine a block
Block newBlock = new Block(previousHash);
newBlock.addTransaction(transaction);
newBlock.mineBlock(difficulty);
```

### Transaction Validation

The system automatically validates:
- **Sufficient Balance**: Prevents spending more than available
- **Valid Signatures**: Ensures transaction authenticity
- **Input Verification**: Validates all transaction inputs
- **UTXO Consistency**: Maintains unspent transaction output integrity

## 🔧 Technical Details

### UTXO Model

This blockchain implements the UTXO (Unspent Transaction Output) model:

1. **Transaction Inputs**: References to previous unspent outputs
2. **Transaction Outputs**: New UTXOs created for recipients
3. **Change Outputs**: Remaining funds returned to sender
4. **Balance Calculation**: Sum of all UTXOs owned by a wallet

### Mining Algorithm

- **Algorithm**: Proof of Work with SHA-256
- **Difficulty**: Adjustable (default: 2 leading zeros)
- **Target**: Hash must start with specified number of zeros
- **Nonce**: Incremented until target is met

### Security Implementation

```java
// Digital signature generation
signature = StringUtil.applyECDSASig(privateKey, transactionData);

// Signature verification
boolean isValid = StringUtil.verifyECDSASig(publicKey, data, signature);

// Hash calculation
String hash = StringUtil.applySha256(blockData);
```

## 📊 Sample Output

```
Creating and Mining Genesis block... 
Transaction Successfully added to Block
Mining block with target: 00 (difficulty: 2)
Block Mined!!! : 008c7d770edd3dfaacb5a3145dd0e9a37bcb830954adaf89a80311f49f0e377b (took 5 ms, 317 attempts)

WalletA's balance is: 100.0

WalletA is Attempting to send funds (40) to WalletB...
Transaction Successfully added to Block
Mining block with target: 00 (difficulty: 2)
Block Mined!!! : 00d7aa6cd38bf6e53b6c987127affc6dc3d0f9d26258cb4921a33a31a7a7e574 (took 1 ms, 69 attempts)

WalletA's balance is: 60.0
WalletB's balance is: 40.0

WalletA Attempting to send more funds (1000) than it has...
#Not Enough funds to send transaction. Transaction Discarded.

WalletB is Attempting to send funds (20) to WalletA...
Transaction Successfully added to Block

WalletA's balance is: 80.0
WalletB's balance is: 20.0

Blockchain is valid
```

## 📦 Dependencies

### External Libraries

| Library | Version | Purpose |
|---------|---------|---------|
| **Gson** | 2.6.2 | JSON serialization and blockchain data export |
| **BouncyCastle** | Latest | Cryptographic operations (ECDSA, key generation) |

### Why These Dependencies?

- **Gson**: Enables easy blockchain serialization to JSON format for data export and analysis
- **BouncyCastle**: Provides robust cryptographic primitives required for:
  - ECDSA key pair generation
  - Digital signature creation and verification
  - Secure random number generation

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🔮 Future Enhancements

### Planned Features
- [ ] **Network Layer**: P2P node communication
- [ ] **Consensus Mechanisms**: Additional consensus algorithms (PoS, PoA)
- [ ] **Smart Contracts**: Basic smart contract functionality
- [ ] **REST API**: HTTP API for blockchain interaction
- [ ] **Database Storage**: Persistent blockchain storage
- [ ] **GUI Interface**: User-friendly graphical interface
- [ ] **Multi-signature**: Multi-signature transaction support
- [ ] **Transaction Fees**: Dynamic fee calculation system

### Performance Improvements
- [ ] **Parallel Mining**: Multi-threaded mining implementation
- [ ] **Memory Optimization**: Reduced memory footprint for large blockchains
- [ ] **Caching**: UTXO caching for faster balance calculations
- [ ] **Compression**: Block and transaction data compression

---

**Note**: This is an educational blockchain implementation designed to demonstrate core blockchain concepts. It is not intended for production use without additional security auditing and optimization.
