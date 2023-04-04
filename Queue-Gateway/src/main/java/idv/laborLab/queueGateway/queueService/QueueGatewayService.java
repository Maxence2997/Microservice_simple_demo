package idv.laborLab.queueGateway.queueService;

public interface QueueGatewayService<T> {

    void convertAndSend(T cargo);
}
