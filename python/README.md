# How to Containerize a Python Application with Paketo Buildpacks

Building even simple Dockerfiles can be a challenge. This article will demonstrate an alternative method of containerizing an application, using Paketo Buildpacks.

Containers have been in use for almost a decade, but containerizing applications can still pose challenges. More specifically,  [Dockerfiles](https://thenewstack.io/docker-basics-how-to-use-dockerfiles/)  — which dictate how container images are built — can be challenging to write properly. Even  [simple Dockerfiles](https://news.ycombinator.com/item?id=20031730)  can be problematic. A  [study](https://ieeexplore.ieee.org/stamp/stamp.jsp?arnumber=8998208)  found that nearly 84% of the projects they analyzed had smells — which are quality problems — in their Dockerfile.

In this article, I will demonstrate an alternative method to Dockerfiles for containerizing an application, following best practices, with just a single command. Before demonstrating this technique, let’s first look at the difficulties associated with containerizing applications using traditional approaches.
## Great Dockerfiles Are Hard to Write

What’s so hard about Dockerfiles?

1.  It’s a craft: writing good Dockerfiles requires deep knowledge and experience. There are a number of  [best practices](https://docs.docker.com/develop/develop-images/dockerfile_best-practices/)  that must be implemented for every Dockerfile. Developers — who are generally the ones writing them — might not have the knowledge or resources to do it right.
    
2.  Security: they can be a security threat if not well written. For example, a common issue with Dockerfiles is that they often use the root user in their instructions, which can create security vulnerabilities and allow an attacker to gain full control over the host system.
    
3.  They are not natively fast: getting fast build time needs work, from ensuring that you use minimal base images, minimize the number of layers, use build caching and set up a multistage build.
    

Learning how to create the perfect Dockerfile can be enjoyable when working with one or two images. However, the excitement wanes as the number of images increases, requiring management across multiple repositories, projects, and stacks, as well as constant maintenance. This is where the open source project  [Paketo Buildpacks](https://paketo.io/)  offers a solution.
## An Easier Way

Before diving into the tutorial, let’s discuss the concept behind  [Buildpacks](https://buildpacks.io/), an open source project maintained by the  [Cloud Native Computing Foundation](https://cncf.io/?utm_content=inline-mention).

Developed by Heroku, Buildpacks transform application source code into images that can run on any cloud platform. They analyze the code, identify what is needed to build and run the software, and then assemble all components into an image. By examining applications, Buildpacks determine the necessary dependencies and configure them in a series of layers, ultimately creating a container image. Buildpacks also feature optimization mechanisms to reduce build time.

While the Cloud Native Buildpacks project offers a specification for Buildpacks, it doesn’t supply ready-to-use Buildpacks; that’s what  [Paketo Buildpacks](https://paketo.io/)  provide. This community-driven project develops production-ready Buildpacks.

Paketo Buildpacks adhere to best practices for each language ecosystem, currently supporting Java, Go, Node.js, .NET, Python, and PHP, among others. The community constantly addresses vulnerabilities in upstream language runtimes and operating system packages, saving you the effort of monitoring for susceptible dependencies.
## Let’s Containerize a Python Application

There are two requirements to use this tutorial:

1.  Have Docker Desktop installed;  [here](https://www.docker.com/products/docker-desktop/)  is a guide to install it.
    
2.  Have pack CLI installed;  [here](https://buildpacks.io/docs/tools/pack/)  is a guide to install it.
    

In this example, we will use a Python application. I provide a sample app for the sake of testing but feel free to use your own.

    git clone git@github.com:sylvainkalache/Containerize-Python-app-with-Paketo.git && cd Containerize-Python-app-with-Paketo

Once you are in the application root directory, run the command:

    pack build my-python-app  --builder paketobuildpacks/builder:base
That’s the only command you need to create an image! Now you can run it as you would usually do.

    docker run  -ti  -p  5000:8000  -e  PORT=8000  my-python-app
Now let’s check that the app is working properly by running this command in another terminal:

    $  curl  0:5000
    Hello,  TheNewStack readers!
    $
You can continue developing your application, and whenever you need a new image, simply run the same pack build command. The initial run of the command might take some time, as it needs to download the _paketobuildpacks/builder:base image_. However, subsequent iterations will be much faster, thanks to advanced caching features implemented by buildpack authors.
## Other Benefits of Using Paketo Buildpacks?

With increasing security standards, numerous engineering organizations have started to depend on SBOMs (software bill of materials) to mitigate the risk of vulnerabilities in their infrastructure. Buildpacks offer a straightforward approach to gaining insights into the contents of images through standard build-time SBOMs, which Buildpack can generate in  [CycloneDX](https://cyclonedx.org/),  [SPDX](https://spdx.dev/), and  [Syft JSON](https://github.com/anchore/syft)  formats.

You can try it on your image by using the following command:

    pack sbom download my-python-app
Another benefit of using Paketo Buildpacks is that you will be using minimal images that contain only what is necessary. For example, while my image based on paketobuildpacks/builder:base was only 295MB, a bare python:3 Docker image is already 933MB.

## Conclusion

Although Dockerfiles have served us well, they are not the most efficient use of a developer’s time. The need to manage and maintain Dockerfiles can become significant, especially with the rise of microservices and distributed architecture. By using Paketo Buildpacks, developers can build better images faster, giving them more time to focus on what adds more value to their projects. And the best part? While we used Python in this article, the same principle can be applied to any project with any supported stack.

This article was originally published on The New Stack https://thenewstack.io/how-to-containerize-a-python-application-with-packeto-buildpacks/

