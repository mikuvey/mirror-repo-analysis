# CCV Data Scientist Assignment REQ71274

This assignment is composed of two parts

## 1. Read a paper, explain, and suggest improvements

The first task in this assignment consists of reading the paper `Feature extraction and machine learning techniques for identifying historic urban environmental hazards: New methods to locate lost fossil fuel infrastructure in US cities`. You can find the PDF included in this repository

We then ask you to prepare a short summary of the problem being solved and the methodology proposed to solve it. While having a general understanding of the historical context described in the paper is important, the emphasis of your explanation should be on the machine learning techniques described in the paper.

After discussing an overview of the methods, we would like you to comment on the advantages and limitations of the proposed methods (think of yourself as a reviewer!) and we would like you to propose potential improvements or new alternative methods if you think a different way of solving the problem would be more effective.

### Deliverables

* Update this README with the short summary (it's fine to use bullet points) and discussion outlined above

## 2. Demonstrate some of you coding abilities

For this part, you have a choice: 

### 2.1 Deep dive into existing, original software

If you have existing software that you have significantly contributed to its design and implementation and are able to share, you can choose to submit the code along with a clear explanation about how it works and the design choices you considered.


#### Requirements:
* The contribution you share is almost entirely your own
  - This excludes for example group projects, or a project where a stencil or starter code was provided
  - If you have a significant standalone contribution to an open source project, that is welcome, but be very clear on what your contribution was and what parts of the code are yours
* Code provides a good example of your familiarity with topics in data science 
* Code can be shared with us
* Language is one of these (if not please consult with us for explicit approval)
    * C/C++
    * C#
    * Java
    * Javascript/Typescript
    * Julia
    * MATLAB
    * Python
    * R
    * Ruby
    * Rust



#### Deliverables:

* Source code. You must send a link to the repository where the code lives. If the repository is not public, you will need to grant us read permissions
* We have shared a starter repository containing this README with you. Please update this README.md with an overview of the software and any major design decisions you made. Please explain explicitly what were your contributions to the project and why you chose this project for submission
* Demonstration of how the code is to be used and results obtained from running the code. 
   * Short (3-7 minute) recording of a walk-through of the features of your code, how it works, what you used it for and what results you obtained. You should tell us in particular what you care about in code, and how you incorporated those things into your solution. **This recording is likely the most impactful part of your application.**

### 2.2 Code something new 

If you prefer to code something new, please follow the instructions below.

The main objective of this assignment is to develop code that builds a training set for a Machine Learning algorithm of your choice using the Data Set outlined in the paper in Section 1. 
Specifically, you will need to:

* Figure out from the details in the paper where/how to download a subset of the original Sandborn images
* Write a preprocessing pipeline with the goal of having a semi-automatic labelling of Manufactured Gas Production (MGP) sites. 
* Have your pipeline create two folders, one with positive samples and the other with the negative samples 

Notes:
* You may use the same methodology described in the paper or choose another method that you think would have superior performance
* Because this is expected to be a semi-automatic preprocessing step, we expect to see errors in the positive/negative sets. That's okay
* You don't need to process a large number of images, just enough to demonstrate that the preprocessing pipeline is working as expected
* We are fans of well formatted code, comments, types, and modularity
* We are fans of a good README.md

#### Deliverables
* Source code. We have shared a starter repository containing this README with you. Please commit all code and output training sets to this repo. 
* Update this README.md with explanations about how to run your code and an overview of the application and any major design decisions you made.
* Short (3-7 minute) recording of a walk-through of the features of your code, how it works, what you used it for and what results you obtained. You should tell us in particular what you care about in code, and how you incorporated those things into your solution. **This recording is likely the most impactful part of your application.**

Please try your best. We'll evaluate whatever you provide us, even if your solution is incomplete.



