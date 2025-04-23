package string;

public class ImmutableString {
	
	/*
	 * string are immutable for following reasons
	 * 1. security - usernames, passwords, filpaths, database urls, queries
	 * 2. string pooling - saves memory by only creating one object, if one or more strings are same
	 * 3. thread safety - Since String objects cannot be changed, they are automatically thread-safe. Multiple threads can safely use the same String without synchronization.
	 * 4. hashcode cashing - Strings are often used as keys in hash-based collections like HashMap. Since a String is immutable, its hashcode is cached after first calculation — boosting performance
	 */

	public static void main(String[] args) {
	}

}
