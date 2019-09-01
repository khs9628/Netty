EventLoopGroup : EventExecutorGroup 인터페이스를 상속받은 인터페이스
NioEventLoopGroup : I/O 작업을 처리하는 다중 스레드 이벤트 루프.
ServerBootstrap : 서버를 설정하는 헬퍼 클래스.
Bootstrap : 클라이언트를 위한 채널을 쉽게 만들어주는 bootstrap
 
NioServerSocketChannel : Nio Selector 기반의 구현을 사용해서, 새로운 연결을 수락하는 ServerSocketChannel의 구현클래스입니다.
NioSocketChannel : Nio selector 기반의 SocketChannel의 구현 클래스
 
Channel : 읽기, 쓰기, 연결 그리고 바인드와 같은 I/O 작업이 가능한 네트워크 소케 또는 구성 요소에 대한 연결고리
Channel 은 아래와 같은 것들을 제공함.
- 현재 채널의 상태 (e.g. is it open?, is it connected?)
- 채널의 구성 매개변수 (e.g. receive buffer size)
- 채널이 지원하는 I/O Operations (e.g. read, write, connect, and bind)
- 채널과 관련된 모든 I/O 이벤트와 요청을 처리하는 ChannelPipeline
 
모든 I/O 작업은 비동기로 동작합니다.
 
ChannelFuture : 비동기 채널 I/O작업의 결과
ChannelPipeline : 채널의 인바운드이벤트 그리고 아웃바운드 작업을 처리하거나 차단하는 채널핸들러의 목록.
SocketChannel : A TCP/IP socket Channel.
ChannelHandlerContext : ChannelHandler가 ChannelPipeline 및 다른 핸들러와
 상호 작용할 수 있도록합니다. 
다른 무엇보다도, 
핸들러는 ChannelPipeline의 다음 ChannelHandler에 통지하고, 
자신이 속한 ChannelPipeline을 동적으로 수정할 수 있습니다.
 
StringDecoder : 받은 ByteBuf 를 String으로 인코딩함. TCP/IP와 같은
스트림 기반의 전송을 사용하는 경우는 DelemiterBaseFrameDecoder 또는
LineBasedFrameDecoder 같은 적절한 ByteToMessageDecoder와 함께 사용해야함.
TCP/IP 소켓에서 텍스트 기반 라인 프로토콜의 일반적인 설정은 아래와 같음.
  ChannelPipeline pipeline = ...;
 
  // 디코더
  pipeline.addLast ( "frameDecoder", new LineBasedFrameDecoder (80));
  pipeline.addLast ( "stringDecoder", new StringDecoder (CharsetUtil.UTF_8));
 
  // 인코더
  pipeline.addLast ( "stringEncoder", new StringEncoder (CharsetUtil.UTF_8));
StringEncoder :StringDecoder와 반대로 요청한 String을 ByteBuf 로 인코딩
DelimiterBasedFrameDecoder : 수신된 ByteBuf 를 하나 이상의 구분 기호로
분리하는 디코더, NULL 또는 개행 문자와 같은 구분 기호로 끝나는 프레임을 
디코딩할 때 특히 유용, 
LineBasedFrameDecoder :수신 한 ByteBufs를 라인 엔딩에서 분리하는 디코더.
"\n"과 "\r\n"이 모두 처리됩니다. 
보다 일반적인 구분 기호 기반 디코더는 DelimiterBasedFrameDecoder를 참조.
 


SslContext : SSLEngine 및 SslHandler 의 팩토리로써 동작하는 안전한 소켓 프로토콜 구현.
SslContextBuilder : 새로은 SslContext 를 생성하기 위한 빌더
 
서버사이드에서 사용할 때
Making your server support SSL/TLS
 
 // In your ChannelInitializer:
 ChannelPipeline p = channel.pipeline();
 SslContext sslCtx = SslContextBuilder.forServer(...).build();
 p.addLast("ssl", sslCtx.newHandler(channel.alloc()));
 
 
클라이언트사이드에서 사용할 때
Making your client support SSL/TLS
 
 // In your ChannelInitializer:
 ChannelPipeline p = channel.pipeline();
 SslContext sslCtx = SslContextBuilder.forClient().build();
 p.addLast("ssl", sslCtx.newHandler(channel.alloc(), host, port));
