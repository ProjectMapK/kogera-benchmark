This project is a benchmark to compare the performance of `jackson-module-kogera` and `jackson-module-kotlin`.  

- [ProjectMapK/jackson\-module\-kogera](https://github.com/ProjectMapK/jackson-module-kogera)
- [FasterXML/jackson\-module\-kotlin](https://github.com/FasterXML/jackson-module-kotlin)

# About benchmark
`./gradlew jmh` to run the benchmark.  
The benchmark results are output to `${project.buildDir}/reports/jmh/`.

The main benchmark is to serialize and deserialize for classes with 1, 5, and 20 properties.  

The extra benchmark relate to options that affect performance.  
Currently, only benchmarks for `strictNullChecks` are implemented.  
Also, these benchmarks are not run by default.

A simple configuration is provided in arguments.  
By rewriting the `isKogera` property, you can run benchmarks on `jackson-module-kotlin` (but exclude benchmarks on non-supported content).  
By rewriting the `isSingleShot` property, you can benchmark the performance on the first run.  
By rewriting the `isOnlyMain` property, you can exec all benchmarks.

The following is an example of running all four benchmarks described in the `README` in succession.

```shell
./gradlew jmh -PisKogera=false -PisSingleShot=true -PisOnlyMain=false;
./gradlew jmh -PisKogera=false -PisSingleShot=false -PisOnlyMain=false;
./gradlew jmh -PisKogera=true -PisSingleShot=true -PisOnlyMain=false;
./gradlew jmh -PisKogera=true -PisSingleShot=false -PisOnlyMain=false
```

If you want to specify more detailed options, edit `build.gradle.kts`.

# Results
The results shown here are for the following commit.  
https://github.com/ProjectMapK/jackson-module-kogera/tree/01cad94664f1504ae4cdd2da144b3d56b4e3efce

The version of `jackson-module-kotlin` is the same as the version of this commit.

## Throughput mode
This is a comparison regarding the case of multiple runs.  
The higher the score, the better.

`kogera` is particularly good at deserialization, being just under three times faster in some use cases.  
Also, the performance degradation with `kogera` is relatively small when `strictNullChecks` is enabled.

There is no significant difference in serialization performance.

### original(2.14.2)
```
Benchmark                                           Mode  Cnt        Score         Error  Units
o.w.extra.deser.StrictNullChecks.array             thrpt    4   812574.138 ±   29118.044  ops/s
o.w.extra.deser.StrictNullChecks.arrayStrict       thrpt    4   672978.182 ±   16180.700  ops/s
o.w.extra.deser.StrictNullChecks.list              thrpt    4   836250.905 ±  108480.042  ops/s
o.w.extra.deser.StrictNullChecks.listStrict        thrpt    4   687453.772 ±   43288.840  ops/s
o.w.extra.deser.StrictNullChecks.map               thrpt    4   770445.698 ±   39971.294  ops/s
o.w.extra.deser.StrictNullChecks.mapStrict         thrpt    4   577546.552 ±   40409.398  ops/s
o.w.main.deser.A_1Props_Constructor.call           thrpt    4   987624.562 ±  143485.971  ops/s
o.w.main.deser.A_1Props_Constructor.call_default   thrpt    4   476802.238 ±   50363.662  ops/s
o.w.main.deser.A_1Props_Function.call              thrpt    4   626060.188 ±   44511.922  ops/s
o.w.main.deser.A_1Props_Function.call_default      thrpt    4   428780.933 ±   41429.577  ops/s
o.w.main.deser.E_5Props_Constructor.call           thrpt    4   395506.466 ±   39029.448  ops/s
o.w.main.deser.E_5Props_Constructor.call_default   thrpt    4   198555.744 ±   18720.818  ops/s
o.w.main.deser.E_5Props_Function.call              thrpt    4   264308.976 ±   11820.506  ops/s
o.w.main.deser.E_5Props_Function.call_default      thrpt    4   181459.675 ±   54730.276  ops/s
o.w.main.deser.T_20Props_Constructor.call          thrpt    4   121090.514 ±    3217.949  ops/s
o.w.main.deser.T_20Props_Constructor.call_default  thrpt    4    62842.705 ±    3689.610  ops/s
o.w.main.deser.T_20Props_Function.call             thrpt    4    81565.752 ±    2781.005  ops/s
o.w.main.deser.T_20Props_Function.call_default     thrpt    4    57289.182 ±    8101.668  ops/s
o.w.main.ser.A_1Props.call                         thrpt    4  2421134.115 ± 1021473.097  ops/s
o.w.main.ser.E_5Props.call                         thrpt    4  1420794.842 ±  299767.080  ops/s
o.w.main.ser.T_20Props.call                        thrpt    4   528270.718 ±   57742.880  ops/s
```

### kogera(2.14.2-alpha3)
```
Benchmark                                           Mode  Cnt        Score        Error  Units
o.w.extra.deser.StrictNullChecks.array             thrpt    4   969377.844 ± 135630.882  ops/s
o.w.extra.deser.StrictNullChecks.list              thrpt    4   994044.444 ± 110198.694  ops/s
o.w.extra.deser.StrictNullChecks.listStrict        thrpt    4   977575.508 ±  42967.650  ops/s
o.w.extra.deser.StrictNullChecks.map               thrpt    4   830879.776 ±  47733.737  ops/s
o.w.extra.deser.StrictNullChecks.mapStrict         thrpt    4   830090.645 ± 351120.265  ops/s
o.w.main.deser.A_1Props_Constructor.call           thrpt    4  1238795.919 ±  48097.646  ops/s
o.w.main.deser.A_1Props_Constructor.call_default   thrpt    4   882499.882 ± 643189.807  ops/s
o.w.main.deser.A_1Props_Function.call              thrpt    4  1219570.038 ± 112768.174  ops/s
o.w.main.deser.A_1Props_Function.call_default      thrpt    4   739144.958 ±  35558.874  ops/s
o.w.main.deser.E_5Props_Constructor.call           thrpt    4   682556.326 ±  26014.593  ops/s
o.w.main.deser.E_5Props_Constructor.call_default   thrpt    4   505643.191 ±  21245.049  ops/s
o.w.main.deser.E_5Props_Function.call              thrpt    4   667479.907 ±  87745.726  ops/s
o.w.main.deser.E_5Props_Function.call_default      thrpt    4   428156.718 ±  30062.636  ops/s
o.w.main.deser.T_20Props_Constructor.call          thrpt    4   248900.179 ±  40745.443  ops/s
o.w.main.deser.T_20Props_Constructor.call_default  thrpt    4   162510.551 ±  15520.394  ops/s
o.w.main.deser.T_20Props_Function.call             thrpt    4   238383.263 ±   7750.150  ops/s
o.w.main.deser.T_20Props_Function.call_default     thrpt    4   154995.515 ±  22701.623  ops/s
o.w.main.ser.A_1Props.call                         thrpt    4  2385486.779 ± 280887.306  ops/s
o.w.main.ser.E_5Props.call                         thrpt    4  1310815.269 ± 590799.931  ops/s
o.w.main.ser.T_20Props.call                        thrpt    4   531560.913 ±  39413.727  ops/s

```

## SingleShot mode
This is a comparison when the process is executed only once.  
The lower the score, the better.

There is not much difference in deserialization, but `kogera` seems to have areas that can be improved in terms of serialization.

### original(2.14.2)
```
Benchmark                                          Mode  Cnt   Score   Error  Units
o.w.extra.deser.StrictNullChecks.array               ss    5  28.703 ± 2.557  ms/op
o.w.extra.deser.StrictNullChecks.arrayStrict         ss    5  27.839 ± 1.624  ms/op
o.w.extra.deser.StrictNullChecks.list                ss    5  24.192 ± 0.666  ms/op
o.w.extra.deser.StrictNullChecks.listStrict          ss    5  23.347 ± 2.019  ms/op
o.w.extra.deser.StrictNullChecks.map                 ss    5  25.550 ± 1.809  ms/op
o.w.extra.deser.StrictNullChecks.mapStrict           ss    5  24.349 ± 1.618  ms/op
o.w.main.deser.A_1Props_Constructor.call             ss    5  19.757 ± 1.487  ms/op
o.w.main.deser.A_1Props_Constructor.call_default     ss    5  21.453 ± 0.692  ms/op
o.w.main.deser.A_1Props_Function.call                ss    5  21.954 ± 2.567  ms/op
o.w.main.deser.A_1Props_Function.call_default        ss    5  22.988 ± 1.145  ms/op
o.w.main.deser.E_5Props_Constructor.call             ss    5  20.496 ± 1.508  ms/op
o.w.main.deser.E_5Props_Constructor.call_default     ss    5  22.392 ± 1.338  ms/op
o.w.main.deser.E_5Props_Function.call                ss    5  23.389 ± 2.350  ms/op
o.w.main.deser.E_5Props_Function.call_default        ss    5  24.606 ± 2.054  ms/op
o.w.main.deser.T_20Props_Constructor.call            ss    5  23.769 ± 1.727  ms/op
o.w.main.deser.T_20Props_Constructor.call_default    ss    5  25.902 ± 3.194  ms/op
o.w.main.deser.T_20Props_Function.call               ss    5  28.047 ± 2.032  ms/op
o.w.main.deser.T_20Props_Function.call_default       ss    5  29.178 ± 1.532  ms/op
o.w.main.ser.A_1Props.call                           ss    5  55.485 ± 5.383  ms/op
o.w.main.ser.E_5Props.call                           ss    5  58.730 ± 4.787  ms/op
o.w.main.ser.T_20Props.call                          ss    5  67.729 ± 4.069  ms/op
```

### kogera(2.14.2)
```
Benchmark                                          Mode  Cnt   Score   Error  Units
o.w.extra.deser.StrictNullChecks.array               ss    5  27.588 ± 7.405  ms/op
o.w.extra.deser.StrictNullChecks.list                ss    5  27.319 ± 6.256  ms/op
o.w.extra.deser.StrictNullChecks.listStrict          ss    5  29.082 ± 7.408  ms/op
o.w.extra.deser.StrictNullChecks.map                 ss    5  28.766 ± 5.968  ms/op
o.w.extra.deser.StrictNullChecks.mapStrict           ss    5  29.314 ± 6.521  ms/op
o.w.main.deser.A_1Props_Constructor.call             ss    5  20.754 ± 1.288  ms/op
o.w.main.deser.A_1Props_Constructor.call_default     ss    5  22.218 ± 1.416  ms/op
o.w.main.deser.A_1Props_Function.call                ss    5  21.690 ± 1.775  ms/op
o.w.main.deser.A_1Props_Function.call_default        ss    5  23.023 ± 1.907  ms/op
o.w.main.deser.E_5Props_Constructor.call             ss    5  23.143 ± 1.138  ms/op
o.w.main.deser.E_5Props_Constructor.call_default     ss    5  24.626 ± 1.767  ms/op
o.w.main.deser.E_5Props_Function.call                ss    5  24.278 ± 0.853  ms/op
o.w.main.deser.E_5Props_Function.call_default        ss    5  25.756 ± 2.082  ms/op
o.w.main.deser.T_20Props_Constructor.call            ss    5  26.500 ± 0.592  ms/op
o.w.main.deser.T_20Props_Constructor.call_default    ss    5  28.822 ± 1.768  ms/op
o.w.main.deser.T_20Props_Function.call               ss    5  28.082 ± 2.407  ms/op
o.w.main.deser.T_20Props_Function.call_default       ss    5  29.691 ± 1.584  ms/op
o.w.main.ser.A_1Props.call                           ss    5  69.995 ± 2.180  ms/op
o.w.main.ser.E_5Props.call                           ss    5  72.591 ± 4.563  ms/op
o.w.main.ser.T_20Props.call                          ss    5  80.042 ± 2.043  ms/op
```
