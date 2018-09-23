srcs: system "find src/main -name '*.kt' | xargs grep import"
tests: system "find src/test -name '*.kt' | xargs grep import"
imports:distinct {7_ first 1_":" vs x} each srcs,tests
isExternalImport:{and[all "logic" <> 5#x;all "unit" <> 4#x]}
externalImports:imports where isExternalImport each imports
-1 each externalImports;
exit 0
