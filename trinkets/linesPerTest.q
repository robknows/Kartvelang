tests: "I"$system "find src/test -name '*.kt' | xargs grep @Test | wc -l"
lines: "I"$1_-6_last system "find src/main -name '*.kt' | xargs wc -l"
-1 string[lines%tests];
exit 0
