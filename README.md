io.generators
==========

![Build status](https://travis-ci.org/generators-io-projects/generators.svg?branch=generators-2)

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
First release was released to the central maven repository on the 29/01/2014.

    io.generators:generators-core:1.0

- Release 1.1 will contain some more generators (composite, padding, truncating) and maybe also basic implementation of the maven-generators-module for generating builders.

- There will be also release 2.0 which will use Java 8 features and will introduce backwards incompatible changes (yep, for the sake of clean API). Estimated release date is end of July 2015.

Snapshots are published/updated quite frequently: 

    io.generators:generators-core:1.1-SNAPSHOT

The snapshot is hosted at Sonatype Nexus Snapshot repository so in order to get it add following repository to your pom:

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

## Chaining/wrapping generators
Some of the generators are just wrappers/decorators around actual generators and are delegating to them.
For instance  '''TransformingGenerator''' allows to apply transformation function to the values generated by the delegate (which in java 8 can be lambda or method reference).

    Generator<String> intAsStringGenerator = new TransformingGenerator<>(Generators.ofInstance(5), Object::toString);

or '''FilteringGenerator''' allows to restrict which values are generated.

## Fluent Interface
There is also special generator '''FluentGenerator''' that provides fluent interface for chaining filters, transformations, unique and type generators.
Generator in the example bellow will generate integers between 13 and 100,000, filter out odd numbers, pad from start with zeroes to 5 digits, make sure that generated values are unique, publish them to custom consumer that will write them to a stream and transform them to a AccountNumber type.

    FluentGenerator<AccountNumber> accountNumberGenerator = FluentGenerator.from(positiveInts(13, 100000))
                    .filter(new EvenNumberPredicate())
                    .transform(new PadStartWithZeros(5))
                    .unique()
                    .publishTo(new StreamConsumer())
                    .ofType(AccountNumber.class);

##Using generators directly in the test
    String accountNumber = Generators.nDigitPositiveInteger(12).next();
or even better:

    AccountNumber accountNumber = MyProjectGenerators.accountNumber().next();

##Using generators in the builders
###First  builder type
The first type of builder generates values during instantiation of the builder.
When build() method is called second time new instance of the Account but with same account number, branch number and name is created.
Builder has to be reconfigured to generate instance that is not equal to the first one:

    AccountBuilder1 accountBuilder = new AccountBuilder1();
    Account sourceAccount = accountBuilder.accountNumber(new AccountNumber(945678)).build();
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

###Second builder type

The second builder generates values, that were not set when configuring builder, during invocation of the build() method.
So if we want two accounts in the same branch but with different account number, the code can look like this:

    AccountBuilder2 accountBuilder = new AccountBuilder2().branchNumber(new BranchNumber("1234"));
    Account sourceAccount = accountBuilder.build();
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

###Third builder type

The third builder generates values using generators during invocation of the build() method as well as second builder
but does not need to perform any extra checks so it is easy to set name to null

    AccountBuilder3 accountBuilder = new AccountBuilder3().missingName().branchNumber(new BranchNumber("1234"));
    Account sourceAccount = accountBuilder.build();
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
