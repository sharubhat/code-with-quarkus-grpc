package com.sb.grpc.client;

import com.sb.HelloGrpc;
import com.sb.HelloReply;
import com.sb.HelloRequest;
import io.quarkus.grpc.GrpcClient;
import io.smallrye.mutiny.Uni;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class ClientResource {

  @GrpcClient
  HelloGrpc hello;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String hello() {
    return "hello";
  }

  @GET
  @Path("/{name}")
  public Uni<String> hello(@PathParam("name") String name) {
    return hello.sayHello(HelloRequest.newBuilder().setName(name).build())
        .onItem().transform(HelloReply::getMessage);
  }
}
