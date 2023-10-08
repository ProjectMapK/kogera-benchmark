This project is a benchmark to compare the performance of `jackson-module-kogera` and `jackson-module-kotlin`.  

- [ProjectMapK/jackson\-module\-kogera](https://github.com/ProjectMapK/jackson-module-kogera)
- [FasterXML/jackson\-module\-kotlin](https://github.com/FasterXML/jackson-module-kotlin)

Instructions for running the benchmarks are provided at the bottom of the README.

# About basic trends
Because `Kogera` offers more features, it often performs worse than `Original` in SingleShot mode.  
This difference is expected to decrease as more features are incorporated into `Original`.  
I have tried to improve it with `Kogera`,
but now the majority of the initialization cost is spent on analysis with `Jackson` and `kotlinx-metadata-jvm`,
so further improvement is difficult.

In Throughput mode, the biggest difference is in the deserialization process.
By eliminating the overhead of function calls with `kotlin-reflect`, a significant improvement is achieved.
On the other hand, there is almost no difference in serialization,
because there is no particular point that can be improved.

# About benchmark results
The currently published scores compare `jackson-module-kogera 2.15.2-beta4` with `jackson-module-kotlin 2.15.2`.

The letters `A` and `E` in the benchmark names are values to adjust the sort order of the results
and represent the number of properties (1 = `A`, 5 = `E`, ...).

The deserialization benchmarks show four patterns of values per number of properties,
depending on the creator invoked and the use of default arguments.  
The `Ctor` and `Func` in the title indicate that the deserialization was done by a constructor or factory function.  
The `present` and `missing` indicate that all arguments were read from the input or all were not read (= default arguments were used).

Raw data is stored in [ci-reports](./ci-reports).  
The spreadsheet used to generate the graphs can be found [here](https://drive.google.com/drive/folders/1mNa-bPybvhNTkGX7qkOfii_iMOI_gODG?usp=drive_link).

## A note on the results on README
Please note that these scores are measured by `GitHub Actions` and are not always the same.  
Also, the cache may stop updating the images for up to 5 minutes,
and the `README` may not have been updated after the benchmark was run,
so the description and images may not always match.

# Results
## Basic use cases
### Throughput mode
#### Deserialize
`Kogera` is particularly good at deserialization.

![](https://docs.google.com/spreadsheets/d/e/2PACX-1vSDpaOENd0a-qO_zK7C5_UkSxEKk7BxLjmyg8XVnPP0jj6J5rgoA8cCnm_lj7lflx6NDjvC1yMUPrce/pubchart?oid=1594997844&format=image)

#### Serialize
As mentioned above, there is little difference between `Kogera` and `Original` as no particular improvements have been made.

![](https://docs.google.com/spreadsheets/d/e/2PACX-1vSLh6CF8Ow6kPD1EUPqyNO3qPC8qtsPeMuFZ1YeqU1OT6eCBBX8jwxo-LzDVi18LsHDlKbJA3TAWcuF/pubchart?oid=1594997844&format=image)

### SingleShot mode
Due to the performance degradation associated with the additional features,
`Kogera` scores are inferior in the comparison at this time.

#### Deserialize
![](https://docs.google.com/spreadsheets/d/e/2PACX-1vQ8Tr1JxT-agRWgoq4T7tEM-WvoYmbsUYlpZ5BzAy1X9sv5J5QU1QoZnF8uNrWXYnxDZy6KWrqd-Dz7/pubchart?oid=1594997844&format=image)

#### Serialize
![](https://docs.google.com/spreadsheets/d/e/2PACX-1vRdynU4YG3xLrDFhZr4996tdpmekH9FeVSstBwA5U_bZ2sPTZpiqJtDTxA_uPyAhOffvgtSbMoWbQkN/pubchart?oid=1594997844&format=image)

## Comparison of normal class and `value class`
This section compares the serialization and deserialization performance using `Kogera`
for the `value class` and the `data class` (`wrapper`).  
Only `present` is compared, since the use of default arguments makes almost no difference in principle.

Overall, the `value class` scores are significantly inferior.  
The reason for this is that the `value class` requires more reflection processing.

In `Kogera`, the `JsonUnbox` annotation has been added to improve the performance of serialization, which requires only `unbox`.
When used, the throughput will outperform the `data class`.

### Throughput mode
#### Deserialize
![](https://docs.google.com/spreadsheets/d/e/2PACX-1vR5Q4ZR1-79wxOyCeKbYUK2x9MPG5X6NP6vjWkd3vrX1Jj9LmmmIGeZzXuDB87YVHUentSg_Km_dBU8/pubchart?oid=1594997844&format=image)

#### Serialize
##### Normal
![](https://docs.google.com/spreadsheets/d/e/2PACX-1vToCbNCtK4vBbFQBZxPBO-7_UQEubTNDM4JQO_HmRT1ydDUyXm8Wqjep166SgUObc1F3m7iUas3XpmT/pubchart?oid=1594997844&format=image)

##### With `JsonUnbox`
![](https://docs.google.com/spreadsheets/d/e/2PACX-1vSfIMsy639_XZK9zXHMGopw6xLQAy9yTm_sFiZSBWwZ_awaxI0a56SAvzrlJIY6hK8bYIu2kfJ0h5lx/pubchart?oid=1594997844&format=image)

### SingleShot mode
Although the difference is smaller in SingleShot mode,
the tendency for inferior scores in the `value class` is still present.

#### Deserialize
![](https://docs.google.com/spreadsheets/d/e/2PACX-1vQrHxIyPMW5u27PWUlT9fXxJX-F7LrRks_mIaYfJ4BhodtptKQluhwRqD76bwGivg8XIi5wTBiNF9Ky/pubchart?oid=1594997844&format=image)

#### Serialize
##### Normal
![](https://docs.google.com/spreadsheets/d/e/2PACX-1vRuZdBEemGH8Rw5V-aLYJHZ1DeVPqo7f9uSSa2w1MKgUV6rZCefjhFfjiqodMF2QAQs3jleqlyqCDRd/pubchart?oid=1594997844&format=image)

##### With `JsonUnbox`
![](https://docs.google.com/spreadsheets/d/e/2PACX-1vRDbC6WJhOLs2AWp1-U7zeUcmSRjkf-jmHk3tgcpR1GA_o-maUndT516WpdJaiK8GQH1T-LF1ewP37x/pubchart?oid=1594997844&format=image)

## Comparison of strictNullChecks
If the `StrictNullChecks` option is enabled,
the deserialization score associated with the `Collection` is reduced because of element checking.  
Also, `Original` will theoretically lower the score for all deserialization operations because of the `null` checks required for all arguments on every call.  
On the other hand, `Kogera` has its own improvements to reduce these score reductions.

Here are the benchmark scores defined in `extra.deser.Collections` and `main.deser`.

### Throughput mode
For the `Collection`, there is almost no difference for `Kogera`, whereas for `Original` there is a relatively large difference.  
There is not much difference except for the `Collection`.

#### Original
![](https://docs.google.com/spreadsheets/d/e/2PACX-1vQXihzOd9NmRsjAGJ9jH2s0iCPutgGQ-2h7MgUCcKTRNeny8vn7w2uJIS9de3SvuIkVLGluVNFAPkgc/pubchart?oid=1594997844&format=image)

#### Kogera
![](https://docs.google.com/spreadsheets/d/e/2PACX-1vQfruDoDVPeQyW27J_Ig7nFOI-ItWDkXhXCk82Z8Fmd8MlfmV-0FS_PsyFlPpbF2v8nU1dtYVFvI08n/pubchart?oid=1594997844&format=image)

### SingleShot mode
The trend is the same as for the throughput mode, but the difference is smaller.

#### Original
![](https://docs.google.com/spreadsheets/d/e/2PACX-1vRzTTZVNDOnI257B-ufXCnwkKWUJvCt5HUhWwaWjlq8hFbgYaJmoUBZZxoKQEvEQqS7beGH7mq9LB-m/pubchart?oid=1594997844&format=image)

#### Kogera
![](https://docs.google.com/spreadsheets/d/e/2PACX-1vTYxIPUawD5d7RkBoEZZso3Ul2aHqHcbSKWBs2ncw07lyTcVdvZw_S-m9-60NJs0JKvPSNaXNJjPCXK/pubchart?oid=1594997844&format=image)

# How to run benchmarks
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
By rewriting the `mapper` property, you can benchmark using the `ObjectMapper` configuration defined in [org.wrongwrong.Mapper](./src/jmh/kotlin/org/wrongwrong/Mapper.kt).  
By rewriting the `benchmarkSet` property, you can choose which benchmarks to run(Locally, it is recommended to run OnlyMain or Full).

Available `mapper` types are as follows

- `Kogera`(default): Not customized `KogeraModule`
- `Original`: Not customized `KotlinModule`
- `KogeraStrictNullCheck`: `KogeraModule` with enabled `strictNullChecks`
- `OriginalStrictNullCheck`: `OriginalModule` with enabled `strictNullChecks`

The following is an example of running all four benchmarks described in the `README` in succession.

```shell
./gradlew jmh -Pmapper=Original -PisSingleShot=true -PbenchmarkSet=Full;
./gradlew jmh -Pmapper=Original -PisSingleShot=false -PbenchmarkSet=Full;
./gradlew jmh -PisSingleShot=true -PbenchmarkSet=Full;
./gradlew jmh -PisSingleShot=false -PbenchmarkSet=Full
```

If you want to specify more detailed options, edit `build.gradle.kts`.
