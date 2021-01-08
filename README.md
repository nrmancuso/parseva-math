![alt text](assets/logo_transparent.png)
<h1 align="center"> parseva-math </h1> <br>
<h4 align="center"> Compute mathematical expressions via abstract syntax tree. </h4><br>  
<hr/>

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![Unlicense][license-shield]][license-url]

## Introduction

**parseva-math** is a small project that I have created to build my experience writing grammar 
for and working with **ANTLR4**.  In a nutshell, parseva-math accepts a mathematical expression
as a string value, and first builds an abstract syntax tree of the expression; then
**parseva-math** walks the tree to evaluate the expression.  I was inspired to begin this
project after finding very few _simple_ examples of building an abstract syntax tree with Java and 
**ANTLR4**. This grammar and initial code is based off of [this](https://stackoverflow.com/a/29996191/) 
awesome stack overflow post.

## Getting Started
You will need to have a version of the JDK >= 15 in order to build parseva-math.<br/><br/>
I plan to make a compiled jar once **parseva-math** reaches version 1.0.  Until then, you must
clone this repository and build it yourself:\
`git clone https://github.com/nmancus1/parseva-math.git && cd parseva-math`

Then, to build the jar:\
`mvn clean compile assembly:single`

Finally, you can run **parseva-math**:\
`java -jar --enable-preview target/parseva-math-<version>-SNAPSHOT-jar-with-dependencies.jar`

Note that `--enable-preview` is required for now, since parseva-math uses JDK15 preview features.

## Usage
**parseva-math** has two different modes of operation; interactive and command line.  Mathematical
functions, such as logarithmic and trigonometric functions are currently supplied by 
[java.lang.Math](https://docs.oracle.com/javase/8/docs/api/java/lang/Math.html).


#### Interactive
To run **parseva-math** in the interactive mode, use `-i` or `--interact`:

`java -jar --enable-preview target/parseva-math-<version>-SNAPSHOT-jar-with-dependencies.jar -i`

This will drop you into the parseva-math terminal, where you can evaluate expressions:
```
> 2 + 2
= 4.000000

> sin(1)
= 0.841471

> sqrt(4)
= 2.000000
```

To exit, simply press enter at an empty prompt.

#### Evaluate via Command Line

To evaluate one expression via command line, use `-e "<expression>"` or 
`--evaluate "<expression>"`:
```
 java -jar --enable-preview target/parseva-math-<version>-SNAPSHOT-jar-with-dependencies.jar -e "atan(0.5)"
 = 0.463648
```

#### Help

Usage help is available using `-h`, `--help`, or simply providing no arguments to **parseva-math**.

## Contributing

If you'd like to contribute, start by searching through the 
[issues](https://github.com/nmancus1/parseva-math/issues)
and [pull requests](https://github.com/nmancus1/parseva-math/pulls) to see whether someone else has
raised a similar idea or question.

If you don't see your idea listed, and you think it fits into the goals of this guide, do one of 
the following:

* **If your contribution is minor,** such as a typo fix, open a pull request.
* **If your contribution is major,** such as a new guide, start by opening an issue first. That way, other people can
  weigh in on the discussion before you do any work.
  
#### Contribution Workflow

1. **Fork** this repo on GitHub
2. **Clone** the project to your own machine from YOUR fork on github. `git clone "url from your fork"` then change into
   the new directory.
3. **Set Upstream** do `git remote add upstream https://github.com/nmancus1/parseva-math.git`
4. **Make new branch** for YOUR code submission; do `git checkout -b 
   <whatever you want to name this branch>`
4. **Commit** changes to your *new* branch, with descriptive message
5. **Push** your work back up to your fork `git push origin <your branch name>`
6. **On Github** Submit a **Pull request** so that the group can review your changes

NOTE: Be sure to merge the latest from "upstream" before making a pull request!
All conflicts should be handled locally, then rebased and pushed to github.

To sync your `main` branch before starting to code, do:

1. `git checkout main`
2. `git fetch --all`
3. `git pull upstream main`
4. `git push` - this syncs your fork on github.
5. Start on #4 above to begin a new contribution.

[Here is a great cheatsheet for git/github.](https://education.github.com/git-cheat-sheet-education.pdf) <br/>
[Here is another article that describes this exact workflow, in more detail.](https://blog.scottlowe.org/2015/01/27/using-fork-branch-git-workflow/)

#### Ground rules & expectations

Before we get started, here are a few things we expect from you (and that you should expect from others):

* Be kind and thoughtful in your conversations around this project. We all come from different backgrounds and projects,
  which means we likely have different perspectives on "how open source is done." Try to listen to others rather than
  convince them that your way is correct.
* If you open a pull request, please ensure that your contribution passes all tests. If there are test failures, you
  will need to address them before we can merge your contribution.
  
## License
Distributed under the [Unlicense](https://unlicense.org/). 

## Built With
* [ANTLR4](https://github.com/antlr/antlr4)
* [OpenJDK15](https://openjdk.java.net/projects/jdk/15/)
* [Maven](https://maven.apache.org/)
* [picocli](https://picocli.info/)

## Acknowledgements
* [Checkstyle](https://github.com/checkstyle/checkstyle)
* [Lucas Trzesniewski](https://github.com/ltrzesniewski)

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[contributors-shield]: https://img.shields.io/github/contributors/nmancus1/repo.svg?style=for-the-badge

[contributors-url]: https://github.com/nmancus1/parseva-math/graphs/contributors

[forks-shield]: https://img.shields.io/github/forks/nmancus1/repo.svg?style=for-the-badge

[forks-url]: https://github.com/nmancus1/parseva-math/network/members

[stars-shield]: https://img.shields.io/github/stars/nmancus1/repo.svg?style=for-the-badge

[stars-url]: https://github.com/nmancus1/parseva-math/stargazers

[issues-shield]: https://img.shields.io/github/issues/nmancus1/repo.svg?style=for-the-badge

[issues-url]: https://github.com/nmancus1/parseva-math/issues

[license-shield]: https://img.shields.io/github/license/nmancus1/repo.svg?style=for-the-badge

[license-url]: https://github.com/nmancus1/parseva-math/blob/main/LICENSE.txt


