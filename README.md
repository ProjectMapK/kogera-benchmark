This project is a benchmark to compare the performance of `jackson-module-kogera` and `jackson-module-kotlin`.  

- [ProjectMapK/jackson\-module\-kogera](https://github.com/ProjectMapK/jackson-module-kogera)
- [FasterXML/jackson\-module\-kotlin](https://github.com/FasterXML/jackson-module-kotlin)

# About benchmark
`./gradlew jmh` to run the benchmark.  
The benchmark results are output to `jmh-reports/reports/jmh/`.

The main benchmark is to serialize and deserialize for classes with 1, 5, and 20 properties.  

The extra benchmark relate to options that affect performance.  
Currently, benchmarks for deserialization of `Collection` (mainly to check the impact of `strictNullChecks`)
and benchmarks for `value class` have been implemented.  
Also, these benchmarks are not run by default,
and benchmarks related to deserialization of `value class` are only run when using `Kogera`.

A simple configuration is provided in arguments.  
By rewriting the `isSingleShot` property, you can benchmark the performance on the first run.  
By rewriting the `isOnlyMain` property, you can exec all benchmarks.  
By rewriting the `mapper` property, you can benchmark using the `ObjectMapper` configuration defined in [org.wrongwrong.Mapper](./src/jmh/kotlin/org/wrongwrong/Mapper.kt).

Available `mapper` types are as follows

- `Kogera`(default): Not customized `KogeraModule`
- `Original`: Not customized `KotlinModule`
- `KogeraStrictNullCheck`: `KogeraModule` with enabled `strictNullChecks`
- `OriginalStrictNullCheck`: `OriginalModule` with enabled `strictNullChecks`

The following is an example of running all four benchmarks described in the `README` in succession.

```shell
./gradlew jmh -Pmapper=Original -PisSingleShot=true -PisOnlyMain=false;
./gradlew jmh -Pmapper=Original -PisSingleShot=false -PisOnlyMain=false;
./gradlew jmh -PisSingleShot=true -PisOnlyMain=false;
./gradlew jmh -PisSingleShot=false -PisOnlyMain=false
```

If you want to specify more detailed options, edit `build.gradle.kts`.

# Results
For ease of comparison, descriptions of kogera-specific implementations are separated.

Please note that these scores were measured locally on @k163377 (`Ryzen 7 3700X`/`DDR4 3600`)
and may vary widely depending on load conditions at the time of measurement.

The letters `A` and `E` in the benchmark names are values to adjust the sort order of the results
and represent the number of properties (1 = `A`, 5 = `E`, ...).

## Throughput mode
This is a comparison regarding the case of multiple runs.  
The higher the score, the better.

`kogera` is particularly good at deserialization, being just under three times faster in some use cases.  

There is no significant difference in serialization performance.

#### original(2.15.2)
```
Benchmark                                                    Mode  Cnt        Score        Error  Units
o.w.extra.deser.Collections.array                           thrpt    4   632178.940 ±  62245.856  ops/s
o.w.extra.deser.Collections.list                            thrpt    4   667036.369 ±  19188.400  ops/s
o.w.extra.deser.Collections.map                             thrpt    4   451129.135 ± 104692.358  ops/s
o.w.extra.deser.wrapper.A_1Props_Constructor.call           thrpt    4   922550.390 ±  61783.797  ops/s
o.w.extra.deser.wrapper.A_1Props_Constructor.call_default   thrpt    4   465611.927 ±   5009.255  ops/s
o.w.extra.deser.wrapper.A_1Props_Function.call              thrpt    4   589765.566 ±  17162.595  ops/s
o.w.extra.deser.wrapper.A_1Props_Function.call_default      thrpt    4   393881.907 ± 134149.714  ops/s
o.w.extra.deser.wrapper.E_5Props_Constructor.call           thrpt    4   368225.002 ±   5881.920  ops/s
o.w.extra.deser.wrapper.E_5Props_Constructor.call_default   thrpt    4   188749.023 ±  37037.545  ops/s
o.w.extra.deser.wrapper.E_5Props_Function.call              thrpt    4   249229.020 ±  35884.608  ops/s
o.w.extra.deser.wrapper.E_5Props_Function.call_default      thrpt    4   181385.096 ±  16514.633  ops/s
o.w.extra.deser.wrapper.T_20Props_Constructor.call          thrpt    4   108650.605 ±  12030.540  ops/s
o.w.extra.deser.wrapper.T_20Props_Constructor.call_default  thrpt    4    60044.770 ±  14198.114  ops/s
o.w.extra.deser.wrapper.T_20Props_Function.call             thrpt    4    78049.482 ±   9220.824  ops/s
o.w.extra.deser.wrapper.T_20Props_Function.call_default     thrpt    4    57743.133 ±  13174.738  ops/s
o.w.extra.ser.value_class.A_1Props.call                     thrpt    4  1562454.910 ±  81909.464  ops/s
o.w.extra.ser.value_class.E_5Props.call                     thrpt    4   570140.902 ±   3993.337  ops/s
o.w.extra.ser.value_class.T_20Props.call                    thrpt    4   171288.772 ±  52201.276  ops/s
o.w.extra.ser.wrapper.A_1Props.call                         thrpt    4  2330247.216 ± 333453.447  ops/s
o.w.extra.ser.wrapper.E_5Props.call                         thrpt    4  1321179.029 ± 214073.093  ops/s
o.w.extra.ser.wrapper.T_20Props.call                        thrpt    4   505000.678 ± 158933.581  ops/s
o.w.main.deser.A_1Props_Constructor.call                    thrpt    4   905299.043 ± 171740.844  ops/s
o.w.main.deser.A_1Props_Constructor.call_default            thrpt    4   459963.596 ±  11314.047  ops/s
o.w.main.deser.A_1Props_Function.call                       thrpt    4   581911.821 ± 116910.948  ops/s
o.w.main.deser.A_1Props_Function.call_default               thrpt    4   410564.308 ±  92110.673  ops/s
o.w.main.deser.E_5Props_Constructor.call                    thrpt    4   375518.970 ±  45620.235  ops/s
o.w.main.deser.E_5Props_Constructor.call_default            thrpt    4   188502.131 ±  18832.117  ops/s
o.w.main.deser.E_5Props_Function.call                       thrpt    4   251917.163 ±  24952.497  ops/s
o.w.main.deser.E_5Props_Function.call_default               thrpt    4   174505.158 ±  54319.444  ops/s
o.w.main.deser.T_20Props_Constructor.call                   thrpt    4   118543.101 ±    886.087  ops/s
o.w.main.deser.T_20Props_Constructor.call_default           thrpt    4    55088.109 ±   8251.193  ops/s
o.w.main.deser.T_20Props_Function.call                      thrpt    4    77837.276 ±   4960.774  ops/s
o.w.main.deser.T_20Props_Function.call_default              thrpt    4    57402.649 ±  11043.743  ops/s
o.w.main.ser.A_1Props.call                                  thrpt    4  2403581.570 ± 209826.075  ops/s
o.w.main.ser.E_5Props.call                                  thrpt    4  1398334.939 ± 121217.438  ops/s
o.w.main.ser.T_20Props.call                                 thrpt    4   535899.029 ±  15887.811  ops/s
```

#### kogera(2.15.2-beta0)
```
Benchmark                                                        Mode  Cnt        Score        Error  Units
o.w.extra.deser.Collections.array                               thrpt    4   774703.806 ±  53488.618  ops/s
o.w.extra.deser.Collections.list                                thrpt    4   814753.082 ±  41098.025  ops/s
o.w.extra.deser.Collections.map                                 thrpt    4   517638.575 ±  16942.752  ops/s
o.w.extra.deser.wrapper.A_1Props_Constructor.call               thrpt    4  1154828.273 ± 103538.570  ops/s
o.w.extra.deser.wrapper.A_1Props_Constructor.call_default       thrpt    4   941950.072 ±   8948.612  ops/s
o.w.extra.deser.wrapper.A_1Props_Function.call                  thrpt    4  1181301.691 ± 109605.685  ops/s
o.w.extra.deser.wrapper.A_1Props_Function.call_default          thrpt    4   725201.087 ± 208014.109  ops/s
o.w.extra.deser.wrapper.E_5Props_Constructor.call               thrpt    4   569194.358 ±  13323.826  ops/s
o.w.extra.deser.wrapper.E_5Props_Constructor.call_default       thrpt    4   476293.570 ±  31753.739  ops/s
o.w.extra.deser.wrapper.E_5Props_Function.call                  thrpt    4   621597.240 ± 101973.454  ops/s
o.w.extra.deser.wrapper.E_5Props_Function.call_default          thrpt    4   419742.854 ±  35974.094  ops/s
o.w.extra.deser.wrapper.T_20Props_Constructor.call              thrpt    4   211068.172 ±   3132.383  ops/s
o.w.extra.deser.wrapper.T_20Props_Constructor.call_default      thrpt    4   156935.677 ±  11461.534  ops/s
o.w.extra.deser.wrapper.T_20Props_Function.call                 thrpt    4   218212.803 ±  29224.244  ops/s
o.w.extra.deser.wrapper.T_20Props_Function.call_default         thrpt    4   149933.160 ±   1427.664  ops/s
o.w.extra.ser.value_class.A_1Props.call                         thrpt    4  1542070.273 ± 301374.031  ops/s
o.w.extra.ser.value_class.E_5Props.call                         thrpt    4   561093.169 ±  32251.231  ops/s
o.w.extra.ser.value_class.T_20Props.call                        thrpt    4   175946.764 ±   8389.082  ops/s
o.w.extra.ser.wrapper.A_1Props.call                             thrpt    4  2311022.481 ± 322766.377  ops/s
o.w.extra.ser.wrapper.E_5Props.call                             thrpt    4  1335430.083 ± 146400.269  ops/s
o.w.extra.ser.wrapper.T_20Props.call                            thrpt    4   498429.621 ±  28797.484  ops/s
o.w.main.deser.A_1Props_Constructor.call                        thrpt    4  1201311.398 ±   7925.383  ops/s
o.w.main.deser.A_1Props_Constructor.call_default                thrpt    4   914607.422 ± 246811.604  ops/s
o.w.main.deser.A_1Props_Function.call                           thrpt    4  1203006.064 ± 110149.909  ops/s
o.w.main.deser.A_1Props_Function.call_default                   thrpt    4   728015.213 ± 164352.526  ops/s
o.w.main.deser.E_5Props_Constructor.call                        thrpt    4   632225.586 ±  30226.840  ops/s
o.w.main.deser.E_5Props_Constructor.call_default                thrpt    4   492309.001 ±  60435.988  ops/s
o.w.main.deser.E_5Props_Function.call                           thrpt    4   653883.254 ± 109035.529  ops/s
o.w.main.deser.E_5Props_Function.call_default                   thrpt    4   443158.840 ±  23201.598  ops/s
o.w.main.deser.T_20Props_Constructor.call                       thrpt    4   246905.387 ±   5696.714  ops/s
o.w.main.deser.T_20Props_Constructor.call_default               thrpt    4   164619.358 ±   2758.071  ops/s
o.w.main.deser.T_20Props_Function.call                          thrpt    4   246103.121 ±  79933.789  ops/s
o.w.main.deser.T_20Props_Function.call_default                  thrpt    4   137523.338 ±  19620.937  ops/s
o.w.main.ser.A_1Props.call                                      thrpt    4  2578192.935 ± 254315.859  ops/s
o.w.main.ser.E_5Props.call                                      thrpt    4  1387010.749 ±  37147.939  ops/s
o.w.main.ser.T_20Props.call                                     thrpt    4   536545.727 ±  10233.785  ops/s
```

## SingleShot mode
This is a comparison when the process is executed only once.  
The lower the score, the better.

There is not much difference in deserialization, but `kogera` seems to have areas that can be improved in terms of serialization.

#### original(2.15.2)
```
Benchmark                                                   Mode  Cnt   Score    Error  Units
o.w.extra.deser.Collections.array                             ss    5  26.595 ±  1.790  ms/op
o.w.extra.deser.Collections.list                              ss    5  22.242 ±  1.675  ms/op
o.w.extra.deser.Collections.map                               ss    5  23.491 ±  0.951  ms/op
o.w.extra.deser.wrapper.A_1Props_Constructor.call             ss    5  19.435 ±  0.555  ms/op
o.w.extra.deser.wrapper.A_1Props_Constructor.call_default     ss    5  21.334 ±  2.385  ms/op
o.w.extra.deser.wrapper.A_1Props_Function.call                ss    5  20.727 ±  1.798  ms/op
o.w.extra.deser.wrapper.A_1Props_Function.call_default        ss    5  21.738 ±  0.667  ms/op
o.w.extra.deser.wrapper.E_5Props_Constructor.call             ss    5  21.112 ±  2.712  ms/op
o.w.extra.deser.wrapper.E_5Props_Constructor.call_default     ss    5  22.529 ±  1.904  ms/op
o.w.extra.deser.wrapper.E_5Props_Function.call                ss    5  24.043 ±  3.071  ms/op
o.w.extra.deser.wrapper.E_5Props_Function.call_default        ss    5  25.476 ±  0.840  ms/op
o.w.extra.deser.wrapper.T_20Props_Constructor.call            ss    5  25.340 ±  4.721  ms/op
o.w.extra.deser.wrapper.T_20Props_Constructor.call_default    ss    5  25.612 ±  2.441  ms/op
o.w.extra.deser.wrapper.T_20Props_Function.call               ss    5  28.285 ±  1.196  ms/op
o.w.extra.deser.wrapper.T_20Props_Function.call_default       ss    5  29.692 ±  3.486  ms/op
o.w.extra.ser.value_class.A_1Props.call                       ss    5  51.549 ±  4.758  ms/op
o.w.extra.ser.value_class.E_5Props.call                       ss    5  55.347 ±  3.913  ms/op
o.w.extra.ser.value_class.T_20Props.call                      ss    5  65.571 ±  3.295  ms/op
o.w.extra.ser.wrapper.A_1Props.call                           ss    5  70.065 ±  4.728  ms/op
o.w.extra.ser.wrapper.E_5Props.call                           ss    5  72.482 ±  2.785  ms/op
o.w.extra.ser.wrapper.T_20Props.call                          ss    5  84.053 ±  7.151  ms/op
o.w.main.deser.A_1Props_Constructor.call                      ss    5  20.146 ±  1.850  ms/op
o.w.main.deser.A_1Props_Constructor.call_default              ss    5  21.853 ±  1.981  ms/op
o.w.main.deser.A_1Props_Function.call                         ss    5  21.343 ±  1.973  ms/op
o.w.main.deser.A_1Props_Function.call_default                 ss    5  22.699 ±  1.055  ms/op
o.w.main.deser.E_5Props_Constructor.call                      ss    5  20.381 ±  1.553  ms/op
o.w.main.deser.E_5Props_Constructor.call_default              ss    5  21.977 ±  1.522  ms/op
o.w.main.deser.E_5Props_Function.call                         ss    5  23.500 ±  2.912  ms/op
o.w.main.deser.E_5Props_Function.call_default                 ss    5  24.888 ±  2.389  ms/op
o.w.main.deser.T_20Props_Constructor.call                     ss    5  25.086 ±  0.641  ms/op
o.w.main.deser.T_20Props_Constructor.call_default             ss    5  26.186 ±  0.397  ms/op
o.w.main.deser.T_20Props_Function.call                        ss    5  28.115 ±  2.802  ms/op
o.w.main.deser.T_20Props_Function.call_default                ss    5  31.844 ± 10.636  ms/op
o.w.main.ser.A_1Props.call                                    ss    5  58.359 ±  6.557  ms/op
o.w.main.ser.E_5Props.call                                    ss    5  61.403 ±  6.337  ms/op
o.w.main.ser.T_20Props.call                                   ss    5  71.533 ±  1.620  ms/op
```

#### kogera(2.15.2-beta0)
```
Benchmark                                                       Mode  Cnt   Score   Error  Units
o.w.extra.deser.Collections.array                                 ss    5  23.730 ± 6.933  ms/op
o.w.extra.deser.Collections.list                                  ss    5  24.502 ± 6.946  ms/op
o.w.extra.deser.Collections.map                                   ss    5  24.664 ± 2.161  ms/op
o.w.extra.deser.wrapper.A_1Props_Constructor.call                 ss    5  19.510 ± 0.911  ms/op
o.w.extra.deser.wrapper.A_1Props_Constructor.call_default         ss    5  20.784 ± 0.839  ms/op
o.w.extra.deser.wrapper.A_1Props_Function.call                    ss    5  20.071 ± 1.268  ms/op
o.w.extra.deser.wrapper.A_1Props_Function.call_default            ss    5  21.623 ± 0.956  ms/op
o.w.extra.deser.wrapper.E_5Props_Constructor.call                 ss    5  22.612 ± 2.622  ms/op
o.w.extra.deser.wrapper.E_5Props_Constructor.call_default         ss    5  24.191 ± 1.682  ms/op
o.w.extra.deser.wrapper.E_5Props_Function.call                    ss    5  22.445 ± 0.445  ms/op
o.w.extra.deser.wrapper.E_5Props_Function.call_default            ss    5  24.420 ± 1.121  ms/op
o.w.extra.deser.wrapper.T_20Props_Constructor.call                ss    5  26.041 ± 1.911  ms/op
o.w.extra.deser.wrapper.T_20Props_Constructor.call_default        ss    5  26.777 ± 1.215  ms/op
o.w.extra.deser.wrapper.T_20Props_Function.call                   ss    5  27.237 ± 1.730  ms/op
o.w.extra.deser.wrapper.T_20Props_Function.call_default           ss    5  28.882 ± 2.652  ms/op
o.w.extra.ser.value_class.A_1Props.call                           ss    5  77.202 ± 2.085  ms/op
o.w.extra.ser.value_class.E_5Props.call                           ss    5  79.852 ± 2.166  ms/op
o.w.extra.ser.value_class.T_20Props.call                          ss    5  88.698 ± 2.354  ms/op
o.w.extra.ser.wrapper.A_1Props.call                               ss    5  74.039 ± 5.832  ms/op
o.w.extra.ser.wrapper.E_5Props.call                               ss    5  75.399 ± 3.453  ms/op
o.w.extra.ser.wrapper.T_20Props.call                              ss    5  84.695 ± 3.618  ms/op
o.w.main.deser.A_1Props_Constructor.call                          ss    5  19.996 ± 1.539  ms/op
o.w.main.deser.A_1Props_Constructor.call_default                  ss    5  21.705 ± 0.942  ms/op
o.w.main.deser.A_1Props_Function.call                             ss    5  20.583 ± 1.012  ms/op
o.w.main.deser.A_1Props_Function.call_default                     ss    5  22.362 ± 0.733  ms/op
o.w.main.deser.E_5Props_Constructor.call                          ss    5  21.895 ± 1.542  ms/op
o.w.main.deser.E_5Props_Constructor.call_default                  ss    5  23.266 ± 0.861  ms/op
o.w.main.deser.E_5Props_Function.call                             ss    5  23.406 ± 0.523  ms/op
o.w.main.deser.E_5Props_Function.call_default                     ss    5  25.291 ± 1.274  ms/op
o.w.main.deser.T_20Props_Constructor.call                         ss    5  27.081 ± 1.342  ms/op
o.w.main.deser.T_20Props_Constructor.call_default                 ss    5  28.525 ± 1.537  ms/op
o.w.main.deser.T_20Props_Function.call                            ss    5  28.038 ± 0.850  ms/op
o.w.main.deser.T_20Props_Function.call_default                    ss    5  29.528 ± 0.653  ms/op
o.w.main.ser.A_1Props.call                                        ss    5  69.803 ± 3.137  ms/op
o.w.main.ser.E_5Props.call                                        ss    5  72.272 ± 3.200  ms/op
o.w.main.ser.T_20Props.call                                       ss    5  80.935 ± 4.979  ms/op
```

## Comparison of normal class and `value class`
This section compares the serialization and deserialization performance using `Kogera 2.15.2-beta0`
for the `value class` and the `data class` (`wrapper`).

### Throughput mode
As a general trend, there is a decrease in performance when using the `value class`.  
This is because more reflection processing is required when processing `value class`.

```
Benchmark                                                        Mode  Cnt        Score         Error  Units
o.w.extra.deser.value_class.A_1Props_Constructor.call           thrpt    4  1013439.378 ±  24297.251  ops/s
o.w.extra.deser.value_class.A_1Props_Constructor.call_default   thrpt    4   881670.353 ± 408044.396  ops/s
o.w.extra.deser.value_class.A_1Props_Function.call              thrpt    4  1018016.319 ±  18952.134  ops/s
o.w.extra.deser.value_class.A_1Props_Function.call_default      thrpt    4   745726.029 ±  28926.906  ops/s
o.w.extra.deser.value_class.E_5Props_Constructor.call           thrpt    4   448769.203 ±  17304.853  ops/s
o.w.extra.deser.value_class.E_5Props_Constructor.call_default   thrpt    4   497035.613 ±  70997.098  ops/s
o.w.extra.deser.value_class.E_5Props_Function.call              thrpt    4   407380.254 ±  94731.335  ops/s
o.w.extra.deser.value_class.E_5Props_Function.call_default      thrpt    4   437543.491 ±  17544.460  ops/s
o.w.extra.deser.value_class.T_20Props_Constructor.call          thrpt    4   143963.355 ±  21931.038  ops/s
o.w.extra.deser.value_class.T_20Props_Constructor.call_default  thrpt    4   161001.900 ±  21003.385  ops/s
o.w.extra.deser.value_class.T_20Props_Function.call             thrpt    4   139137.647 ±  12716.396  ops/s
o.w.extra.deser.value_class.T_20Props_Function.call_default     thrpt    4   157931.536 ±   3811.466  ops/s
o.w.extra.deser.wrapper.A_1Props_Constructor.call               thrpt    4  1154828.273 ± 103538.570  ops/s
o.w.extra.deser.wrapper.A_1Props_Constructor.call_default       thrpt    4   941950.072 ±   8948.612  ops/s
o.w.extra.deser.wrapper.A_1Props_Function.call                  thrpt    4  1181301.691 ± 109605.685  ops/s
o.w.extra.deser.wrapper.A_1Props_Function.call_default          thrpt    4   725201.087 ± 208014.109  ops/s
o.w.extra.deser.wrapper.E_5Props_Constructor.call               thrpt    4   569194.358 ±  13323.826  ops/s
o.w.extra.deser.wrapper.E_5Props_Constructor.call_default       thrpt    4   476293.570 ±  31753.739  ops/s
o.w.extra.deser.wrapper.E_5Props_Function.call                  thrpt    4   621597.240 ± 101973.454  ops/s
o.w.extra.deser.wrapper.E_5Props_Function.call_default          thrpt    4   419742.854 ±  35974.094  ops/s
o.w.extra.deser.wrapper.T_20Props_Constructor.call              thrpt    4   211068.172 ±   3132.383  ops/s
o.w.extra.deser.wrapper.T_20Props_Constructor.call_default      thrpt    4   156935.677 ±  11461.534  ops/s
o.w.extra.deser.wrapper.T_20Props_Function.call                 thrpt    4   218212.803 ±  29224.244  ops/s
o.w.extra.deser.wrapper.T_20Props_Function.call_default         thrpt    4   149933.160 ±   1427.664  ops/s
o.w.extra.ser.value_class.A_1Props.call                         thrpt    4  1542070.273 ± 301374.031  ops/s
o.w.extra.ser.value_class.E_5Props.call                         thrpt    4   561093.169 ±  32251.231  ops/s
o.w.extra.ser.value_class.T_20Props.call                        thrpt    4   175946.764 ±   8389.082  ops/s
o.w.extra.ser.wrapper.A_1Props.call                             thrpt    4  2311022.481 ± 322766.377  ops/s
o.w.extra.ser.wrapper.E_5Props.call                             thrpt    4  1335430.083 ± 146400.269  ops/s
o.w.extra.ser.wrapper.T_20Props.call                            thrpt    4   498429.621 ±  28797.484  ops/s
```

### SingleShot mode
The scores here also show the same trend as in Throughput mode.

```
Benchmark                                                       Mode  Cnt   Score   Error  Units
o.w.extra.deser.value_class.A_1Props_Constructor.call             ss    5  21.536 ± 1.167  ms/op
o.w.extra.deser.value_class.A_1Props_Constructor.call_default     ss    5  21.369 ± 0.869  ms/op
o.w.extra.deser.value_class.A_1Props_Function.call                ss    5  22.138 ± 1.700  ms/op
o.w.extra.deser.value_class.A_1Props_Function.call_default        ss    5  22.048 ± 0.968  ms/op
o.w.extra.deser.value_class.E_5Props_Constructor.call             ss    5  23.586 ± 1.184  ms/op
o.w.extra.deser.value_class.E_5Props_Constructor.call_default     ss    5  24.127 ± 0.943  ms/op
o.w.extra.deser.value_class.E_5Props_Function.call                ss    5  25.334 ± 2.609  ms/op
o.w.extra.deser.value_class.E_5Props_Function.call_default        ss    5  25.058 ± 1.727  ms/op
o.w.extra.deser.value_class.T_20Props_Constructor.call            ss    5  29.147 ± 2.248  ms/op
o.w.extra.deser.value_class.T_20Props_Constructor.call_default    ss    5  28.703 ± 2.084  ms/op
o.w.extra.deser.value_class.T_20Props_Function.call               ss    5  30.369 ± 2.057  ms/op
o.w.extra.deser.value_class.T_20Props_Function.call_default       ss    5  30.127 ± 1.217  ms/op
o.w.extra.deser.wrapper.A_1Props_Constructor.call                 ss    5  19.510 ± 0.911  ms/op
o.w.extra.deser.wrapper.A_1Props_Constructor.call_default         ss    5  20.784 ± 0.839  ms/op
o.w.extra.deser.wrapper.A_1Props_Function.call                    ss    5  20.071 ± 1.268  ms/op
o.w.extra.deser.wrapper.A_1Props_Function.call_default            ss    5  21.623 ± 0.956  ms/op
o.w.extra.deser.wrapper.E_5Props_Constructor.call                 ss    5  22.612 ± 2.622  ms/op
o.w.extra.deser.wrapper.E_5Props_Constructor.call_default         ss    5  24.191 ± 1.682  ms/op
o.w.extra.deser.wrapper.E_5Props_Function.call                    ss    5  22.445 ± 0.445  ms/op
o.w.extra.deser.wrapper.E_5Props_Function.call_default            ss    5  24.420 ± 1.121  ms/op
o.w.extra.deser.wrapper.T_20Props_Constructor.call                ss    5  26.041 ± 1.911  ms/op
o.w.extra.deser.wrapper.T_20Props_Constructor.call_default        ss    5  26.777 ± 1.215  ms/op
o.w.extra.deser.wrapper.T_20Props_Function.call                   ss    5  27.237 ± 1.730  ms/op
o.w.extra.deser.wrapper.T_20Props_Function.call_default           ss    5  28.882 ± 2.652  ms/op
o.w.extra.ser.value_class.A_1Props.call                           ss    5  77.202 ± 2.085  ms/op
o.w.extra.ser.value_class.E_5Props.call                           ss    5  79.852 ± 2.166  ms/op
o.w.extra.ser.value_class.T_20Props.call                          ss    5  88.698 ± 2.354  ms/op
o.w.extra.ser.wrapper.A_1Props.call                               ss    5  74.039 ± 5.832  ms/op
o.w.extra.ser.wrapper.E_5Props.call                               ss    5  75.399 ± 3.453  ms/op
o.w.extra.ser.wrapper.T_20Props.call                              ss    5  84.695 ± 3.618  ms/op
```

## Comparison of strictNullChecks
When the `strictNullChecks` option is enabled,
`Original` checks all arguments, which has a performance impact on the entire deserialization, not just `Collection`.
On the other hand, `Kogera` has been improved in this respect and the impact is minimal.

### Throughput mode
In `Original`, we can see a significant drop in the deserialization score, especially for `Collection`.
On the other hand, for `Kogera` the impact is much smaller.

For deserializations other than `Collection`, the effect on the score seemed to be small for `Original` as well.

#### original(2.15.2)
```
Benchmark                                                    Mode  Cnt        Score        Error  Units
o.w.extra.deser.Collections.array                           thrpt    4   550730.871 ±  21551.272  ops/s
o.w.extra.deser.Collections.list                            thrpt    4   519278.322 ±   1406.656  ops/s
o.w.extra.deser.Collections.map                             thrpt    4   371273.009 ± 115903.146  ops/s
o.w.extra.deser.wrapper.A_1Props_Constructor.call           thrpt    4   898298.430 ± 193417.616  ops/s
o.w.extra.deser.wrapper.A_1Props_Constructor.call_default   thrpt    4   474135.416 ±  18661.830  ops/s
o.w.extra.deser.wrapper.A_1Props_Function.call              thrpt    4   586365.166 ±  32450.605  ops/s
o.w.extra.deser.wrapper.A_1Props_Function.call_default      thrpt    4   378213.294 ± 273907.655  ops/s
o.w.extra.deser.wrapper.E_5Props_Constructor.call           thrpt    4   349061.664 ±  72554.751  ops/s
o.w.extra.deser.wrapper.E_5Props_Constructor.call_default   thrpt    4   192198.552 ±   2314.314  ops/s
o.w.extra.deser.wrapper.E_5Props_Function.call              thrpt    4   237483.311 ±   1656.351  ops/s
o.w.extra.deser.wrapper.E_5Props_Function.call_default      thrpt    4   179887.537 ±  72374.037  ops/s
o.w.extra.deser.wrapper.T_20Props_Constructor.call          thrpt    4   108228.599 ±   7858.069  ops/s
o.w.extra.deser.wrapper.T_20Props_Constructor.call_default  thrpt    4    61019.864 ±   1283.670  ops/s
o.w.extra.deser.wrapper.T_20Props_Function.call             thrpt    4    78003.037 ±  12361.846  ops/s
o.w.extra.deser.wrapper.T_20Props_Function.call_default     thrpt    4    57431.070 ±  19051.032  ops/s
o.w.main.deser.A_1Props_Constructor.call                    thrpt    4   932785.411 ±  33857.528  ops/s
o.w.main.deser.A_1Props_Constructor.call_default            thrpt    4   455477.594 ±  15891.398  ops/s
o.w.main.deser.A_1Props_Function.call                       thrpt    4   586334.692 ±  28323.153  ops/s
o.w.main.deser.A_1Props_Function.call_default               thrpt    4   420064.159 ±   9227.156  ops/s
o.w.main.deser.E_5Props_Constructor.call                    thrpt    4   377548.033 ±  26105.048  ops/s
o.w.main.deser.E_5Props_Constructor.call_default            thrpt    4   191004.929 ±  20673.584  ops/s
o.w.main.deser.E_5Props_Function.call                       thrpt    4   249488.164 ±  21704.509  ops/s
o.w.main.deser.E_5Props_Function.call_default               thrpt    4   177545.573 ±  14188.155  ops/s
o.w.main.deser.T_20Props_Constructor.call                   thrpt    4   118073.329 ±   7413.178  ops/s
o.w.main.deser.T_20Props_Constructor.call_default           thrpt    4    56486.054 ±  12496.907  ops/s
o.w.main.deser.T_20Props_Function.call                      thrpt    4    79316.547 ±    935.675  ops/s
o.w.main.deser.T_20Props_Function.call_default              thrpt    4    55024.917 ±  15367.166  ops/s
```

#### kogera(2.15.2-beta0)

```
Benchmark                                                        Mode  Cnt        Score         Error  Units
o.w.extra.deser.Collections.array                               thrpt    4   764599.383 ±   38375.567  ops/s
o.w.extra.deser.Collections.list                                thrpt    4   809465.942 ±   71000.116  ops/s
o.w.extra.deser.Collections.map                                 thrpt    4   509147.961 ±  114990.510  ops/s
o.w.extra.deser.value_class.A_1Props_Constructor.call           thrpt    4  1012982.154 ±   23160.995  ops/s
o.w.extra.deser.value_class.A_1Props_Constructor.call_default   thrpt    4   944650.437 ±   52652.835  ops/s
o.w.extra.deser.value_class.A_1Props_Function.call              thrpt    4  1005607.785 ±   69082.352  ops/s
o.w.extra.deser.value_class.A_1Props_Function.call_default      thrpt    4   747376.724 ±   45368.383  ops/s
o.w.extra.deser.value_class.E_5Props_Constructor.call           thrpt    4   435808.028 ±   76348.843  ops/s
o.w.extra.deser.value_class.E_5Props_Constructor.call_default   thrpt    4   492601.919 ±  122981.935  ops/s
o.w.extra.deser.value_class.E_5Props_Function.call              thrpt    4   434320.698 ±   99923.049  ops/s
o.w.extra.deser.value_class.E_5Props_Function.call_default      thrpt    4   445731.596 ±   51794.044  ops/s
o.w.extra.deser.value_class.T_20Props_Constructor.call          thrpt    4   136344.681 ±   80196.120  ops/s
o.w.extra.deser.value_class.T_20Props_Constructor.call_default  thrpt    4   168191.762 ±    6856.829  ops/s
o.w.extra.deser.value_class.T_20Props_Function.call             thrpt    4   136163.841 ±   12759.220  ops/s
o.w.extra.deser.value_class.T_20Props_Function.call_default     thrpt    4   135084.105 ±  153309.066  ops/s
o.w.extra.deser.wrapper.A_1Props_Constructor.call               thrpt    4  1189611.404 ±   81882.425  ops/s
o.w.extra.deser.wrapper.A_1Props_Constructor.call_default       thrpt    4   915552.739 ±  191995.431  ops/s
o.w.extra.deser.wrapper.A_1Props_Function.call                  thrpt    4  1183577.806 ±   65050.980  ops/s
o.w.extra.deser.wrapper.A_1Props_Function.call_default          thrpt    4   750690.207 ±   44529.612  ops/s
o.w.extra.deser.wrapper.E_5Props_Constructor.call               thrpt    4   600533.682 ±  102641.980  ops/s
o.w.extra.deser.wrapper.E_5Props_Constructor.call_default       thrpt    4   441593.256 ±  128082.904  ops/s
o.w.extra.deser.wrapper.E_5Props_Function.call                  thrpt    4   623159.760 ±   13408.369  ops/s
o.w.extra.deser.wrapper.E_5Props_Function.call_default          thrpt    4   425195.247 ±    1696.939  ops/s
o.w.extra.deser.wrapper.T_20Props_Constructor.call              thrpt    4   219219.179 ±   29759.781  ops/s
o.w.extra.deser.wrapper.T_20Props_Constructor.call_default      thrpt    4   158895.782 ±    5895.261  ops/s
o.w.extra.deser.wrapper.T_20Props_Function.call                 thrpt    4   204506.129 ±   93528.887  ops/s
o.w.extra.deser.wrapper.T_20Props_Function.call_default         thrpt    4   141740.015 ±   44885.229  ops/s
o.w.main.deser.A_1Props_Constructor.call                        thrpt    4  1192947.912 ±   34649.006  ops/s
o.w.main.deser.A_1Props_Constructor.call_default                thrpt    4   927304.654 ±   60345.741  ops/s
o.w.main.deser.A_1Props_Function.call                           thrpt    4   967243.453 ± 1483914.335  ops/s
o.w.main.deser.A_1Props_Function.call_default                   thrpt    4   742011.409 ±   38127.492  ops/s
o.w.main.deser.E_5Props_Constructor.call                        thrpt    4   648028.532 ±  112512.650  ops/s
o.w.main.deser.E_5Props_Constructor.call_default                thrpt    4   471074.122 ±   99947.992  ops/s
o.w.main.deser.E_5Props_Function.call                           thrpt    4   655664.316 ±   48253.256  ops/s
o.w.main.deser.E_5Props_Function.call_default                   thrpt    4   442859.628 ±   10063.669  ops/s
o.w.main.deser.T_20Props_Constructor.call                       thrpt    4   239354.970 ±   28687.280  ops/s
o.w.main.deser.T_20Props_Constructor.call_default               thrpt    4   167232.044 ±   19515.931  ops/s
o.w.main.deser.T_20Props_Function.call                          thrpt    4   246267.839 ±   62647.072  ops/s
o.w.main.deser.T_20Props_Function.call_default                  thrpt    4   158231.627 ±   14202.386  ops/s
```

### SingleShot mode
In SingleShot mode, there was not much difference.

#### original(2.15.2)
```
Benchmark                                                   Mode  Cnt   Score   Error  Units
o.w.extra.deser.Collections.array                             ss    5  27.245 ± 2.660  ms/op
o.w.extra.deser.Collections.list                              ss    5  22.997 ± 1.616  ms/op
o.w.extra.deser.Collections.map                               ss    5  24.612 ± 1.735  ms/op
o.w.extra.deser.wrapper.A_1Props_Constructor.call             ss    5  19.424 ± 1.382  ms/op
o.w.extra.deser.wrapper.A_1Props_Constructor.call_default     ss    5  20.744 ± 2.299  ms/op
o.w.extra.deser.wrapper.A_1Props_Function.call                ss    5  20.658 ± 1.504  ms/op
o.w.extra.deser.wrapper.A_1Props_Function.call_default        ss    5  21.900 ± 2.175  ms/op
o.w.extra.deser.wrapper.E_5Props_Constructor.call             ss    5  20.675 ± 1.840  ms/op
o.w.extra.deser.wrapper.E_5Props_Constructor.call_default     ss    5  21.934 ± 1.098  ms/op
o.w.extra.deser.wrapper.E_5Props_Function.call                ss    5  23.671 ± 1.070  ms/op
o.w.extra.deser.wrapper.E_5Props_Function.call_default        ss    5  25.141 ± 1.475  ms/op
o.w.extra.deser.wrapper.T_20Props_Constructor.call            ss    5  23.516 ± 0.580  ms/op
o.w.extra.deser.wrapper.T_20Props_Constructor.call_default    ss    5  24.814 ± 1.358  ms/op
o.w.extra.deser.wrapper.T_20Props_Function.call               ss    5  28.212 ± 1.805  ms/op
o.w.extra.deser.wrapper.T_20Props_Function.call_default       ss    5  29.052 ± 1.146  ms/op
o.w.main.deser.A_1Props_Constructor.call                      ss    5  19.703 ± 1.708  ms/op
o.w.main.deser.A_1Props_Constructor.call_default              ss    5  20.993 ± 0.930  ms/op
o.w.main.deser.A_1Props_Function.call                         ss    5  20.941 ± 0.904  ms/op
o.w.main.deser.A_1Props_Function.call_default                 ss    5  22.803 ± 2.616  ms/op
o.w.main.deser.E_5Props_Constructor.call                      ss    5  20.598 ± 0.931  ms/op
o.w.main.deser.E_5Props_Constructor.call_default              ss    5  21.898 ± 1.127  ms/op
o.w.main.deser.E_5Props_Function.call                         ss    5  23.138 ± 2.129  ms/op
o.w.main.deser.E_5Props_Function.call_default                 ss    5  24.690 ± 2.061  ms/op
o.w.main.deser.T_20Props_Constructor.call                     ss    5  24.048 ± 1.424  ms/op
o.w.main.deser.T_20Props_Constructor.call_default             ss    5  25.901 ± 0.598  ms/op
o.w.main.deser.T_20Props_Function.call                        ss    5  27.978 ± 2.426  ms/op
o.w.main.deser.T_20Props_Function.call_default                ss    5  29.408 ± 1.737  ms/op
```

#### kogera(2.15.2-beta0)
```
Benchmark                                                       Mode  Cnt   Score   Error  Units
o.w.extra.deser.Collections.array                                 ss    5  24.420 ± 7.453  ms/op
o.w.extra.deser.Collections.list                                  ss    5  25.318 ± 9.616  ms/op
o.w.extra.deser.Collections.map                                   ss    5  25.872 ± 7.343  ms/op
o.w.extra.deser.value_class.A_1Props_Constructor.call             ss    5  21.356 ± 1.151  ms/op
o.w.extra.deser.value_class.A_1Props_Constructor.call_default     ss    5  21.227 ± 0.631  ms/op
o.w.extra.deser.value_class.A_1Props_Function.call                ss    5  22.081 ± 1.366  ms/op
o.w.extra.deser.value_class.A_1Props_Function.call_default        ss    5  22.019 ± 1.340  ms/op
o.w.extra.deser.value_class.E_5Props_Constructor.call             ss    5  24.411 ± 1.791  ms/op
o.w.extra.deser.value_class.E_5Props_Constructor.call_default     ss    5  23.685 ± 1.633  ms/op
o.w.extra.deser.value_class.E_5Props_Function.call                ss    5  25.294 ± 1.074  ms/op
o.w.extra.deser.value_class.E_5Props_Function.call_default        ss    5  24.935 ± 0.842  ms/op
o.w.extra.deser.value_class.T_20Props_Constructor.call            ss    5  29.166 ± 0.600  ms/op
o.w.extra.deser.value_class.T_20Props_Constructor.call_default    ss    5  28.832 ± 0.674  ms/op
o.w.extra.deser.value_class.T_20Props_Function.call               ss    5  30.135 ± 0.437  ms/op
o.w.extra.deser.value_class.T_20Props_Function.call_default       ss    5  30.119 ± 1.114  ms/op
o.w.extra.deser.wrapper.A_1Props_Constructor.call                 ss    5  19.294 ± 0.218  ms/op
o.w.extra.deser.wrapper.A_1Props_Constructor.call_default         ss    5  20.937 ± 1.205  ms/op
o.w.extra.deser.wrapper.A_1Props_Function.call                    ss    5  20.151 ± 1.063  ms/op
o.w.extra.deser.wrapper.A_1Props_Function.call_default            ss    5  21.994 ± 1.284  ms/op
o.w.extra.deser.wrapper.E_5Props_Constructor.call                 ss    5  21.989 ± 2.006  ms/op
o.w.extra.deser.wrapper.E_5Props_Constructor.call_default         ss    5  23.766 ± 1.252  ms/op
o.w.extra.deser.wrapper.E_5Props_Function.call                    ss    5  22.629 ± 1.446  ms/op
o.w.extra.deser.wrapper.E_5Props_Function.call_default            ss    5  24.247 ± 1.230  ms/op
o.w.extra.deser.wrapper.T_20Props_Constructor.call                ss    5  26.169 ± 1.888  ms/op
o.w.extra.deser.wrapper.T_20Props_Constructor.call_default        ss    5  27.266 ± 1.115  ms/op
o.w.extra.deser.wrapper.T_20Props_Function.call                   ss    5  27.453 ± 0.916  ms/op
o.w.extra.deser.wrapper.T_20Props_Function.call_default           ss    5  28.766 ± 1.087  ms/op
o.w.main.deser.A_1Props_Constructor.call                          ss    5  20.045 ± 0.961  ms/op
o.w.main.deser.A_1Props_Constructor.call_default                  ss    5  21.710 ± 0.746  ms/op
o.w.main.deser.A_1Props_Function.call                             ss    5  20.695 ± 0.586  ms/op
o.w.main.deser.A_1Props_Function.call_default                     ss    5  22.508 ± 0.607  ms/op
o.w.main.deser.E_5Props_Constructor.call                          ss    5  21.770 ± 0.962  ms/op
o.w.main.deser.E_5Props_Constructor.call_default                  ss    5  23.367 ± 0.801  ms/op
o.w.main.deser.E_5Props_Function.call                             ss    5  23.615 ± 0.965  ms/op
o.w.main.deser.E_5Props_Function.call_default                     ss    5  25.364 ± 0.919  ms/op
o.w.main.deser.T_20Props_Constructor.call                         ss    5  26.934 ± 1.730  ms/op
o.w.main.deser.T_20Props_Constructor.call_default                 ss    5  28.602 ± 1.350  ms/op
o.w.main.deser.T_20Props_Function.call                            ss    5  27.922 ± 2.287  ms/op
o.w.main.deser.T_20Props_Function.call_default                    ss    5  29.771 ± 2.104  ms/op
```
