module jhttpclient.mutiny {
    requires java.net.http;
    requires io.smallrye.mutiny;
    requires jhttpclient;

    exports io.github.nestigogroup.jhttpclient.mutiny;
}