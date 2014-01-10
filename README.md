io.generators
==========

io.generators is a tiny framework for generating random or sequential primitives and types in Java.

It supports generating unique values, constants, performing filtering or transformation of the generated values, broadcasting the values to consumers and more.
New generators can be implemented on top of alredy existing generators or as direct implementation of the simple '''Generator<T>''' interface.

Generators are especially useful for testing:
 -   Randomly generated values mean that there is no need to tear-down test data when running integration tests and allow tests to commit there transactions if required.
 -   Populated DB is also closer to real life scenario and may highlight bugs in the code
 -   When used in builders they allow to focus on configuration of only those values that are important for the test which makes tests easier to read and maintain
 -   When used consistently across services they ensure that that all generated values are conforming to the same format (i.e. account numbers)
 -   They make refactoring easier as it is easier to make format or type changes (i.e. all my tests now need to use branch number that is 6 digits long not 4, or the the amount is no longer BigDecimal but Amount type)


Releases
-------------
First release (0.1) is planned for the middle of the January 2014. Interfaces/packages/library dependencies are not stable yet and may change. Therefore major release version is 0.x not 1.x

Snapshots are published/updated quite frequently: 

    io.generators:generators-core:0.1-SNAPSHOT

The snapshot is hosted at Sonatype Nexus Snapshot repository so in order to get it add following reppository to your pom:

    <repositories>
        <repository>
            <id>sonatype-nexus-snapshots</id>
            <name>Sonatype Nexus Snapshots</name>
            <url>https://oss.sonatype.org/content/repositories/snapshots</url>
            <releases>
                <enabled>false</enabled>
            </releases>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
        </repository>
    </repositories>


Basic usage
==========
Generators can be used directly in the test or in the builders that create domain objects, DTOs.
No matter where they are used it is always useful to create utility class that groups them together:

    public static class MyProjectGenerators {
        public static Generator<AccountNumber> accountNumber = FluentGenerator.from(Generators.nDigitPositiveInteger(12))
                .unique()
                .ofType(AccountNumber.class);
        public static Generator<BranchNumber> branchNumber = FluentGenerator.from(Generators.nDigitPositiveInteger(4))
                 .ofType(BranchNumber.class);
    }


##Directly in the test
    String accountNumber = Generators.nDigitPositiveInteger(12).next();
or even better:

    AccountNumber accountNumber = MyProjectGenerators.accountNumber().next();

##In the builders
###First type
The first type of builder generates values during instantiation of the builder.
When build() method is called second time new instance of the Account but with same account number, branch number and name is created.
Builder has to be reconfigured to generate instance that is not equal to the first one:

    AccountBuilder1 accountBuilder = new AccountBuilder1();
    Account sourceAccount = accountBuilder.accountNumber(new AccountNumber(945678)).next();
    Account destinationAccount = accountBuilder.accountNumber(new AccountNumber(1234567890)).build();

Where builder is implemented like this:

    public class AccountBuilder1 {
         private BranchNumber branchNumber = MyProjectGenerators.branchNumber.next();
         private AccountNumber accountNumber = MyProjectGenerators.accountNumber.next();
         private String name = Generators.alphabetic10.next();

         public Account build() {
             return new Account(branchNumber, accountNumber, name);
         }

         public AccountBuilder1 accountNumber(AccountNumber accountNumber) {
             this.accountNumber = accountNumber;
             return this;
         }

         public AccountBuilder1 branchNumber(BranchNumber branchNumber) {
             this.branchNumber = branchNumber;
             return this;
         }
    }


But maybe for this test case we don't care what is the branch number but for some it is important. In order to enforce specifying branch number we can use second builder.

###Second type

The second builder generates values, that were not set when configuring builder, during invocation of the build() method.
So if we want two accounts in the same branch but with different account number, the code can look like this:

    AccountBuilder1 accountBuilder = new AccountBuilder1().branchNumber(new BranchNumber("1234"));
    Account sourceAccount = accountBuilder.next();
    Account destinationAccount = accountBuilder.build();

This won't be possible with first builder. However with this type of builder it is harder to support creating account with null name (possible with some inner flags in the builder)
Where second type of builder is implemented like this:

    public class AccountBuilder2 {
        private AccountNumber accountNumber;
        private BranchNumber branchNumber;
        private String name;

        public Account build() {
            return new Account(
                    firstNonNull(branchNumber, MyProjectGenerators.branchNumber.next()),
                    firstNonNull(accountNumber, MyProjectGenerators.accountNumber.next()),
                    firstNonNull(name, Generators.alphabetic10.next()));
        }

        public AccountBuilder2 accountNumber(AccountNumber accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }
    }

###Third type

The third builder generates values using generators during invocation of the build() method as well as second builder
but does not need to perform any extra checks so it is easy to set name to null

    AccountBuilder1 accountBuilder = new AccountBuilder1().missingName().branchNumber(new BranchNumber("1234"));
    Account sourceAccount = accountBuilder.next();
    Account destinationAccount = accountBuilder.build();

Where third type of builder is implemented like this:

    public class AccountBuilder3 {
        private Generator<BranchNumber> branchNumber = MyProjectGenerators.branchNumber;
        private Generator<AccountNumber> accountNumber = MyProjectGenerators.accountNumber;
        private Generator<String> name = Generators.alphabetic10;

        public Account build() {
            return new Account(
                    branchNumber.next(),
                    accountNumber.next(),
                    name.next());
        }

        public AccountBuilder3 accountNumber(AccountNumber accountNumber) {
            this.accountNumber = Generators.ofInstance(accountNumber);
            return this;
        }

        public AccountBuilder3 missingName() {
            this.accountNumber = Generators.ofInstance(null);
            return this;
        }
    }
