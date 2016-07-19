package com.github.nginate.zookeeper;

import com.github.dockerjava.api.model.*;
import com.github.nginate.commons.docker.DockerUtils;
import com.github.nginate.commons.docker.client.DockerClientOptions;
import com.github.nginate.commons.docker.client.NDockerClient;
import com.github.nginate.commons.docker.client.options.CreateContainerOptions;
import com.github.nginate.commons.docker.wrapper.DockerContainer;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.URI;

public class ZookeeperClientIT {

    private static final int ZOOKEEPER_PORT = 2181;
    private static final int ZOOKEEPER_HOST_PORT = 58901;

    private DockerContainer zookeeperContainer;
    private ZookeeperClient zookeeperClient;

    @BeforeClass
    public void before() throws Exception {
        DockerClientOptions clientOptions = DockerUtils.defaultClientOptions();
        String dockerHost = clientOptions.getDockerUri().startsWith("http") ?
                InetAddress.getByName(URI.create(clientOptions.getDockerUri()).getHost()).getHostAddress() :
                "localhost";
        NDockerClient dockerClient = DockerUtils.createClient(clientOptions);
        zookeeperContainer = DockerUtils.forceCreateContainer(dockerClient, zookeeperContainerConfiguration());
        ZookeeperClientConfig clientConfig = ZookeeperClientConfig.custom(dockerHost)
                .port(ZOOKEEPER_HOST_PORT)
                .build();
        zookeeperClient = new ZookeeperClient(clientConfig);
    }

    @AfterClass(alwaysRun = true)
    public void tearDownDockerContainer() throws Exception {
        zookeeperContainer.printLogs();
        zookeeperContainer.remove();
    }

    @Test
    public void shouldConnect() throws Exception {

    }

    private static CreateContainerOptions zookeeperContainerConfiguration() throws SocketException {
        return CreateContainerOptions.builder()
                .image("nginate/kafka-docker-bundle:0.10")
                .name("kafka-bundle")
                .portBindings(new PortBinding[]{
                        new PortBinding(new Ports.Binding(ZOOKEEPER_HOST_PORT), ExposedPort.tcp(ZOOKEEPER_PORT))
                })
                .env("KAFKA_HEAP_OPTS", "-Xmx256M -Xms128M")
                .logConfig(new LogConfig(LogConfig.LoggingType.DEFAULT))
                .restartPolicy(RestartPolicy.noRestart())
                .build();
    }
}