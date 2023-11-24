package models.repositorios;

import models.configs.Config;

public class RepoConfig extends RepoGenerico<Config>{
    public static RepoConfig INSTANCE = new RepoConfig();

    public RepoConfig() {
        super(Config.class);
    }
}
