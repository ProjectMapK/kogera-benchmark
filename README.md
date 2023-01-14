This project is a benchmark to compare the performance of `jackson-module-kogera` and `jackson-module-kotlin`.  

- [ProjectMapK/jackson\-module\-kogera](https://github.com/ProjectMapK/jackson-module-kogera)
- [FasterXML/jackson\-module\-kotlin](https://github.com/FasterXML/jackson-module-kotlin)

# About benchmark
`./gradlew jmh` to run the benchmark.  
The benchmark results are output to `${project.buildDir}/reports/jmh/`.

The benchmark is to serialize and deserialize for classes with 1, 5, and 20 properties.

A simple configuration is provided in `build.gradle.kts`.  
By rewriting the `isKogera` property, you can compare with the original.  
By rewriting the `isSingleShot` property, you can compare the performance of the first run.

# Results
The results shown here are for the following commit.  
https://github.com/ProjectMapK/jackson-module-kogera/tree/01cad94664f1504ae4cdd2da144b3d56b4e3efce

The version of `jackson-module-kotlin` is the same as the version of this commit.

## Throughput mode
This is a comparison regarding the case of multiple runs.  
The higher the score, the better.

`kogera` is particularly good at deserialization, being just under three times faster in some use cases.  
There is no significant difference in serialization performance.

### original
```
Benchmark                                      Mode  Cnt        Score         Error  Units
o.w.deser.A_1Props_Constructor.call           thrpt    4  1004448.997 ±   62302.965  ops/s
o.w.deser.A_1Props_Constructor.call_default   thrpt    4   470013.881 ±   21215.829  ops/s
o.w.deser.A_1Props_Function.call              thrpt    4   601115.818 ±  324246.530  ops/s
o.w.deser.A_1Props_Function.call_default      thrpt    4   431063.618 ±   21028.812  ops/s
o.w.deser.E_5Props_Constructor.call           thrpt    4   394190.611 ±  111782.973  ops/s
o.w.deser.E_5Props_Constructor.call_default   thrpt    4   192541.176 ±    7146.446  ops/s
o.w.deser.E_5Props_Function.call              thrpt    4   265730.867 ±    6649.522  ops/s
o.w.deser.E_5Props_Function.call_default      thrpt    4   174851.584 ±   16428.057  ops/s
o.w.deser.T_20Props_Constructor.call          thrpt    4   123025.159 ±    2603.021  ops/s
o.w.deser.T_20Props_Constructor.call_default  thrpt    4    61165.917 ±   20147.113  ops/s
o.w.deser.T_20Props_Function.call             thrpt    4    81975.770 ±    4336.209  ops/s
o.w.deser.T_20Props_Function.call_default     thrpt    4    61767.920 ±   10132.015  ops/s
o.w.ser.A_1Props.call                         thrpt    4  2510385.542 ± 1499403.743  ops/s
o.w.ser.E_5Props.call                         thrpt    4  1418520.416 ±  143547.097  ops/s
o.w.ser.T_20Props.call                        thrpt    4   524663.645 ±  207778.475  ops/s
```

### kogera
```
Benchmark                                      Mode  Cnt        Score        Error  Units
o.w.deser.A_1Props_Constructor.call           thrpt    4  1198306.379 ±  69580.268  ops/s
o.w.deser.A_1Props_Constructor.call_default   thrpt    4   956347.556 ±  77137.115  ops/s
o.w.deser.A_1Props_Function.call              thrpt    4  1207766.596 ± 299961.475  ops/s
o.w.deser.A_1Props_Function.call_default      thrpt    4   749100.105 ±  31547.685  ops/s
o.w.deser.E_5Props_Constructor.call           thrpt    4   559296.144 ± 443550.354  ops/s
o.w.deser.E_5Props_Constructor.call_default   thrpt    4   512798.772 ±  18555.107  ops/s
o.w.deser.E_5Props_Function.call              thrpt    4   659824.405 ±  17599.124  ops/s
o.w.deser.E_5Props_Function.call_default      thrpt    4   442497.817 ±  28554.608  ops/s
o.w.deser.T_20Props_Constructor.call          thrpt    4   247582.873 ±  54476.044  ops/s
o.w.deser.T_20Props_Constructor.call_default  thrpt    4   163579.480 ±  19821.215  ops/s
o.w.deser.T_20Props_Function.call             thrpt    4   238197.067 ±  30899.348  ops/s
o.w.deser.T_20Props_Function.call_default     thrpt    4   158440.185 ±   7509.137  ops/s
o.w.ser.A_1Props.call                         thrpt    4  2382325.786 ± 331437.125  ops/s
o.w.ser.E_5Props.call                         thrpt    4  1432610.001 ± 146758.819  ops/s
o.w.ser.T_20Props.call                        thrpt    4   538362.961 ±  34987.874  ops/s
```

## SingleShot mode
This is a comparison when the process is executed only once.  
The lower the score, the better.

There is not much difference in deserialization, but `kogera` seems to have areas that can be improved in terms of serialization.

### original
```
Benchmark                                     Mode  Cnt   Score   Error  Units
o.w.deser.A_1Props_Constructor.call             ss    5  20.021 ± 1.290  ms/op
o.w.deser.A_1Props_Constructor.call_default     ss    5  21.482 ± 1.143  ms/op
o.w.deser.A_1Props_Function.call                ss    5  21.852 ± 2.234  ms/op
o.w.deser.A_1Props_Function.call_default        ss    5  22.854 ± 1.051  ms/op
o.w.deser.E_5Props_Constructor.call             ss    5  20.855 ± 1.978  ms/op
o.w.deser.E_5Props_Constructor.call_default     ss    5  22.347 ± 1.030  ms/op
o.w.deser.E_5Props_Function.call                ss    5  23.280 ± 0.715  ms/op
o.w.deser.E_5Props_Function.call_default        ss    5  24.636 ± 1.458  ms/op
o.w.deser.T_20Props_Constructor.call            ss    5  23.945 ± 1.502  ms/op
o.w.deser.T_20Props_Constructor.call_default    ss    5  25.913 ± 1.791  ms/op
o.w.deser.T_20Props_Function.call               ss    5  28.484 ± 2.875  ms/op
o.w.deser.T_20Props_Function.call_default       ss    5  29.572 ± 1.218  ms/op
o.w.ser.A_1Props.call                           ss    5  55.035 ± 3.071  ms/op
o.w.ser.E_5Props.call                           ss    5  59.042 ± 3.804  ms/op
o.w.ser.T_20Props.call                          ss    5  67.898 ± 2.479  ms/op
```

### kogera
```
Benchmark                                     Mode  Cnt   Score   Error  Units
o.w.deser.A_1Props_Constructor.call             ss    5  20.756 ± 1.813  ms/op
o.w.deser.A_1Props_Constructor.call_default     ss    5  21.861 ± 0.689  ms/op
o.w.deser.A_1Props_Function.call                ss    5  22.034 ± 3.631  ms/op
o.w.deser.A_1Props_Function.call_default        ss    5  23.171 ± 0.886  ms/op
o.w.deser.E_5Props_Constructor.call             ss    5  22.634 ± 0.604  ms/op
o.w.deser.E_5Props_Constructor.call_default     ss    5  24.435 ± 1.532  ms/op
o.w.deser.E_5Props_Function.call                ss    5  24.239 ± 1.774  ms/op
o.w.deser.E_5Props_Function.call_default        ss    5  25.658 ± 1.174  ms/op
o.w.deser.T_20Props_Constructor.call            ss    5  26.508 ± 0.943  ms/op
o.w.deser.T_20Props_Constructor.call_default    ss    5  27.831 ± 0.957  ms/op
o.w.deser.T_20Props_Function.call               ss    5  28.895 ± 2.484  ms/op
o.w.deser.T_20Props_Function.call_default       ss    5  30.278 ± 1.634  ms/op
o.w.ser.A_1Props.call                           ss    5  70.356 ± 5.123  ms/op
o.w.ser.E_5Props.call                           ss    5  72.016 ± 3.723  ms/op
o.w.ser.T_20Props.call                          ss    5  81.907 ± 5.221  ms/op
```
