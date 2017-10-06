package grpc.client;

import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import util.data.ParseReply;
import util.data.ParseRequest;
import util.data.ParsingServiceGrpc;

import java.util.ArrayList;
import java.util.List;

public class ParsingServiceClient {
    private final ManagedChannel channel;
    private final ParsingServiceGrpc.ParsingServiceStub asyncStub;
    private final ParsingServiceGrpc.ParsingServiceBlockingStub blockingStub;

    public ParsingServiceClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext(true));
    }

    /** Construct client for accessing RouteGuide server using the existing channel. */
    public ParsingServiceClient(ManagedChannelBuilder<?> channelBuilder) {
        channel = channelBuilder.build();
        asyncStub = ParsingServiceGrpc.newStub(channel);
        blockingStub = ParsingServiceGrpc.newBlockingStub(channel);
    }

    public List<String> parse(String string){
        ParseRequest request = ParseRequest.newBuilder().setData(ByteString.copyFromUtf8(string)).build();
        ParseReply reply = blockingStub.parse(request);
        List<String> list = new ArrayList<>();
        if(reply.getDataCount()>0){
            for(ByteString bytes : reply.getDataList()){
                list.add(bytes.toStringUtf8());
            }
        }
        return list;
    }
}
