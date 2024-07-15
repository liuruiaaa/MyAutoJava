package io.renren.common.commandrunner;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NettyRunner implements CommandLineRunner {
    private final MyServer myServer;
    /**
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
      //  myServer.start();
    }
}
